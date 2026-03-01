package com.gnexus.app.ui.screens.library.panes

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AppBarWithSearch
import androidx.compose.material3.DropdownMenuGroup
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenuPopup
import androidx.compose.material3.ExpandedFullScreenContainedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberContainedSearchBarState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gnexus.app.R
import com.gnexus.app.ui.screens.library.components.TabScreen
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

enum class Destination(
	val route: String,
	val label: String,
	val icon: Int,
	val contentDescription: String
) {
	PSN("psn", "PSN", R.drawable.playstation, "PlayStation"),
	STEAM("steam", "Steam", R.drawable.steam, "Steam"),
	XBOX("xbox", "Xbox", R.drawable.xbox, "Xbox"),
	NS("NS", "NS", R.drawable.nintendo_switch, "Nintendo Switch"),
	EPIC("EPIC", "Epic", R.drawable.epicgames, "Epic Games")
}

private const val LIBRARY_BASE_ROUTE = "library"
private const val PLATFORM_ARG = "platform"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
	ExperimentalFoundationApi::class)
@Composable
fun GameFeedPane(
	windowSizeClass: WindowSizeClass,
	onGameClick: (Int) -> Unit,
	onTrophyClick: (Int) -> Unit,
	onGuideClick: (Int) -> Unit,
) {
	// --- 1. 统一和精简状态管理 ---
	val destinations = remember { Destination.entries }
	val pagerState = rememberPagerState(pageCount = { destinations.size })
	val coroutineScope = rememberCoroutineScope()

	val startDestination = Destination.PSN
	var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }
	var checked by remember { mutableStateOf(false) }

	val textFieldState = rememberTextFieldState()
	val searchBarState = rememberContainedSearchBarState()
	val scope = rememberCoroutineScope()
	SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()
	val appBarWithSearchColors =
		SearchBarDefaults.appBarWithSearchColors(
			searchBarColors = SearchBarDefaults.containedColors(state = searchBarState)
		)
	val inputField =
		@Composable {
			SearchBarDefaults.InputField(
				modifier = Modifier,
				enabled = false,
				textFieldState = textFieldState,
				searchBarState = searchBarState,
				colors = appBarWithSearchColors.searchBarColors.inputFieldColors,
				onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
				placeholder = {
					Text(modifier = Modifier.clearAndSetSemantics {}, text = "Search")
				},
				leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "语音搜索") },
				trailingIcon = {
					Icon(
						Icons.Filled.KeyboardVoice,
						contentDescription = "语音搜索"
					)
				},
			)
		}
	val groupLabels = listOf("排序选项")
	val groupInteractionSource = remember { MutableInteractionSource() }
	val groupItemLabels = listOf(listOf("最近游玩", "A-Z排序", "Z-A排序"))
	var checkedMenuItem by remember {
		mutableIntStateOf(0)
	}

	Scaffold(
		topBar = {
			AppBarWithSearch(
				state = searchBarState,
				colors = appBarWithSearchColors,
				inputField = inputField,
				actions = {
					SplitButtonLayout(
						leadingButton = {
							SplitButtonDefaults.LeadingButton(onClick = { /* Do Nothing */ }) {
								Icon(
									Icons.AutoMirrored.Filled.ViewList,
									modifier = Modifier.size(SplitButtonDefaults.LeadingIconSize),
									contentDescription = "Localized description",
								)
							}
						},
						trailingButton = {
							val description = ""
							TooltipBox(
								positionProvider =
									TooltipDefaults.rememberTooltipPositionProvider(
										TooltipAnchorPosition.Above
									),
								tooltip = { PlainTooltip { Text("排序方式") } },
								state = rememberTooltipState(),
							) {
								SplitButtonDefaults.TrailingButton(
									checked = checked,
									onCheckedChange = { checked = it },
									modifier =
										Modifier.semantics {
											stateDescription =
												if (checked) "Expanded" else "Collapsed"
											contentDescription = description
										},
								) {
									val rotation: Float by
									animateFloatAsState(
										targetValue = if (checked) 180f else 0f,
										label = "Trailing Icon Rotation",
									)
									Icon(
										Icons.AutoMirrored.Filled.Sort,
										modifier =
											Modifier
												.size(SplitButtonDefaults.TrailingIconSize)
												.graphicsLayer {
													this.rotationZ = rotation
												},
										contentDescription = "Localized description",
									)
								}
							}
						},
					)
					DropdownMenuPopup(
						expanded = checked,
						onDismissRequest = { checked = false },
					) {
						val groupCount = groupLabels.size
						groupLabels.fastForEachIndexed { groupIndex, label ->
							DropdownMenuGroup(
								shapes = MenuDefaults.groupShape(groupIndex, groupCount),
								interactionSource = groupInteractionSource
							) {
								MenuDefaults.Label {
									Text(
										label,
										style = MaterialTheme.typography.bodySmall
									)
								}
								HorizontalDivider(
									modifier = Modifier.padding(MenuDefaults.HorizontalDividerPadding)
								)
								val groupItemCount = groupItemLabels[groupIndex].size
								groupItemLabels[groupIndex].fastForEachIndexed { itemIndex, itemLabel ->
									DropdownMenuItem(
										text = { Text(itemLabel) },
										shapes = MenuDefaults.itemShape(itemIndex, groupItemCount),
										checked = checkedMenuItem == itemIndex,
										onCheckedChange = {
											checkedMenuItem = itemIndex
											checked = false
										},
										checkedLeadingIcon = {
											Icon(
												Icons.Filled.Check,
												contentDescription = null,
											)
										},
										trailingIcon = {
											Icon(Icons.Filled.AccessTime, contentDescription = null)
										},
									)
								}
							}
						}
					}
				},
			)
			ExpandedFullScreenContainedSearchBar(
				state = searchBarState,
				inputField = inputField,
				colors = appBarWithSearchColors.searchBarColors,
			) {

			}
		},
	) { innerPadding ->
		Surface(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding),
			shape = if (windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium) {
				// 平板 / 大屏 / 折叠展开
				RoundedCornerShape(24.dp)
			} else {
				// 直板手机
				RoundedCornerShape(
					topStart = 24.dp,
					topEnd = 24.dp,
					bottomStart = 0.dp,
					bottomEnd = 0.dp
				)
			},
			color = MaterialTheme.colorScheme.surfaceContainerHigh
		) {
			Column {
				PrimaryScrollableTabRow(
					selectedTabIndex = pagerState.currentPage,
				) {
					Destination.entries.forEachIndexed { index, destination ->
						Tab(
							selected = selectedDestination == index,
							onClick = {
								coroutineScope.launch {
									pagerState.animateScrollToPage(index)
								}
							},
							icon = {
								Image(
									painter = painterResource(destination.icon),
									contentDescription = null,
									modifier = Modifier.size(24.dp),
									colorFilter = ColorFilter.tint(
										MaterialTheme.colorScheme.onBackground
									),
								)
							}
						)
					}
				}
				HorizontalPager(
					state = pagerState,
					modifier = Modifier
						.fillMaxSize()
						.weight(1f)
						.clipToBounds()
				) { pageIndex ->
					val currentDestination = destinations[pageIndex]
					TabScreen(
						onGameClick,
						onTrophyClick,
						onGuideClick,
					)
				}
			}
		}
	}
}

/**
 * 一个绘制渐变遮罩的辅助 Composable
 * @param start true表示是左侧的起始遮罩，false表示是右侧的结束遮罩
 */
@Composable
private fun FadingEdge(start: Boolean, width: androidx.compose.ui.unit.Dp = 24.dp) {
	val colors = listOf(
		MaterialTheme.colorScheme.surfaceContainerHigh,
		Color.Transparent
	)
	Box(
		modifier = Modifier
			.fillMaxHeight()
			.width(width)
			.background(
				brush = Brush.horizontalGradient(
					colors = if (start) colors else colors.asReversed()
				)
			)
	)
}