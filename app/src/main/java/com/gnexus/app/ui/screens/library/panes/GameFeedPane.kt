package com.gnexus.app.ui.screens.library.panes

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
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
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberContainedSearchBarState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.gnexus.app.R
import com.gnexus.app.ui.components.Avatar
import com.gnexus.app.ui.screens.library.LibraryViewModel
import com.gnexus.app.ui.screens.library.components.GameLibraryCard
import com.gnexus.app.ui.screens.library.components.GameLibrarySkeletonLoader
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TabScreen(
	onGameClick: (Int) -> Unit,
	onTrophyClick: (Int) -> Unit,
	onGuideClick: (Int) -> Unit,
	viewModel: LibraryViewModel = hiltViewModel()
) {
	val games = viewModel.games.collectAsLazyPagingItems()
	TextFieldState("")
	val listState = rememberLazyListState()

	rememberCoroutineScope()

	val pullToRefreshState = rememberPullToRefreshState()

	// --- 状态提升：从ViewModel获取刷新状态和触发刷新 ---
	val isRefreshing by viewModel.isRefreshing.collectAsState()
	rememberPullToRefreshState()

	Column(
		modifier = Modifier.fillMaxSize(),
	) {
		Row(
			modifier = Modifier
				.padding(horizontal = 24.dp, vertical = 8.dp)
				.height(50.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Avatar(
				"https://psn-rsc.prod.dl.playstation.net/psn-rsc/avatar/HP9000/PPSA01971_00-DEATHSDCAVATAR11_4E1229519D7858373038_xl.png",
				Modifier.size(42.dp)
			)
			Column(
				modifier = Modifier.padding(start = 8.dp)
			) {
				Row(
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = "FujiiCat",
						fontSize = MaterialTheme.typography.bodySmall.fontSize,
						modifier = Modifier
							.padding(end = 8.dp)
					)
					Image(
						painter = painterResource(R.drawable.psn),
						contentDescription = "psn_plus",
						modifier = Modifier.size(15.dp),
					)
				}
				Text("jonas-lang-garfi", fontSize = MaterialTheme.typography.bodySmall.fontSize)
			}
			Spacer(Modifier.weight(1f))
			Column(
				horizontalAlignment = Alignment.End
			) {
				Text("200 款游戏", fontSize = MaterialTheme.typography.bodySmall.fontSize)
				Text("2000 个奖杯", fontSize = MaterialTheme.typography.bodySmall.fontSize)
			}
		}
		PullToRefreshBox(
			isRefreshing = isRefreshing,
			onRefresh = { viewModel.onRefresh() },
			state = pullToRefreshState,
			contentAlignment = Alignment.TopStart,
			modifier = Modifier.fillMaxSize(),
			indicator = {
				PullToRefreshDefaults.LoadingIndicator(
					state = pullToRefreshState,
					color = MaterialTheme.colorScheme.primary,
					isRefreshing = isRefreshing,
					modifier = Modifier
						.align(Alignment.TopCenter)
//						.padding(top = 80.dp)
				)
			}
		) {
			// --- 核心优化：处理加载状态并显示骨架屏 ---
			val refreshLoadState = games.loadState.refresh

			when (// 1. 正在刷新，或初始加载 (即使有缓存也显示骨架屏)
				refreshLoadState) {
				is LoadState.Loading -> {
					GameLibrarySkeletonLoader()
				}

				// 2. 刷新失败
				is LoadState.Error -> {
					// 如果Paging没有任何缓存数据，则显示全屏错误
					if (games.itemCount == 0) {
						Box(
							modifier = Modifier.fillMaxSize(),
							contentAlignment = Alignment.Center
						) {
							Text(text = "加载失败，请下拉重试")
						}
					}
					// 如果有缓存数据，Paging会自动显示旧数据，这里我们什么都不做，
					// 让用户看到旧列表。如果你不希望这样，可以在这里也返回骨架屏。
					else {
						LazyColumn(
							state = listState,
							contentPadding = PaddingValues(
								bottom = 12.dp,
								start = 24.dp,
								end = 24.dp,
								top = 0.dp
							),
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							items(games.itemSnapshotList) { item ->
								GameLibraryCard(
									item,
									onGameClick,
									onTrophyClick,
									onGuideClick
								)
							}
						}
					}
				}

				// 3. 刷新成功或从缓存加载完成
				else -> {
					// 检查列表是否为空
					if (games.itemCount == 0) {
						Box(
							modifier = Modifier.fillMaxSize(),
							contentAlignment = Alignment.Center
						) {
							Text(text = "你的游戏库是空的")
						}
					} else {
						LazyColumn(
							state = listState,
							contentPadding = PaddingValues(
								bottom = 12.dp,
								start = 24.dp,
								end = 24.dp,
								top = 0.dp
							),
							horizontalAlignment = Alignment.CenterHorizontally
						) {
							items(games.itemSnapshotList) { item ->
								GameLibraryCard(
									item,
									onGameClick,
									onTrophyClick,
									onGuideClick
								)
							}
						}
					}
				}
			}
		}
	}
}

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GameFeedPane(
	windowSizeClass: WindowSizeClass,
	onGameClick: (Int) -> Unit,
	onTrophyClick: (Int) -> Unit,
	onGuideClick: (Int) -> Unit,
) {
//	val tabRowState = remember { PrimaryScrollableTabRow.ScrollState(0) }
	rememberCoroutineScope() // 用于点击时自动滚动Tab

	val navController = rememberNavController()
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
					selectedTabIndex = selectedDestination,
//					scrollState = tabRowState,
				) {
					Destination.entries.forEachIndexed { index, destination ->
						Tab(
							selected = selectedDestination == index,
							onClick = {
								// 如果点击的不是当前选中的Tab，才执行导航
								if (selectedDestination != index) {
									selectedDestination = index
									navController.navigate("$LIBRARY_BASE_ROUTE/${destination.route}") {
//										// 2. 这是Tab导航的最佳实践，能完美处理返回栈和状态保存
										popUpTo(navController.graph.startDestinationId) {
											saveState = true // 离开时保存当前Tab的状态
										}
										launchSingleTop = true // 避免在栈顶重复创建实例
										restoreState = true // 返回时恢复之前保存的状态
									}
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
				NavHost(
					navController = navController,
					startDestination = "$LIBRARY_BASE_ROUTE/${startDestination.route}",
					enterTransition = { fadeIn(animationSpec = tween(220, delayMillis = 90)) },
					exitTransition = { fadeOut(animationSpec = tween(90)) }
				) {
					composable(route = "$LIBRARY_BASE_ROUTE/{$PLATFORM_ARG}") {
						TabScreen(
							onGameClick = onGameClick,
							onTrophyClick = onTrophyClick,
							onGuideClick = onGuideClick
						)
					}
				}
			}
		}
	}
}
