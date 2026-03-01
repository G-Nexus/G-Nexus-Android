package com.gnexus.app.ui.screens.library.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.GridView
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
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.appBarWithSearchColors
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.SplitButtonDefaults
import androidx.compose.material3.SplitButtonLayout
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.util.fastForEachIndexed
import com.gnexus.app.ui.screens.library.ViewMode
import kotlinx.coroutines.launch

@OptIn(
	ExperimentalMaterial3Api::class,
	ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun LibraryTopAppBar(
	searchBarState: SearchBarState,
	checked: Boolean,
	onCheckedChange: (Boolean) -> Unit,
	checkedMenuItem: Int,
	onMenuItemChange: (Int) -> Unit,
	currentViewMode: ViewMode,
	onViewModeChange: () -> Unit
) {
	val scope = rememberCoroutineScope()
	val appBarWithSearchColors =
		appBarWithSearchColors(
			searchBarColors = SearchBarDefaults.containedColors(state = searchBarState)
		)
	rememberCoroutineScope()
	val textFieldState = rememberTextFieldState()
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

	AppBarWithSearch(
		state = searchBarState,
		colors = appBarWithSearchColors,
		inputField = inputField,
		actions = {
			SplitButtonLayout(
				leadingButton = {
					SplitButtonDefaults.LeadingButton(onClick = onViewModeChange) {
						val icon = when (currentViewMode) {
							ViewMode.LIST -> Icons.AutoMirrored.Filled.ViewList
							ViewMode.GRID -> Icons.Default.GridView
						}
						val contentDesc = when (currentViewMode) {
							ViewMode.LIST -> "Switch to Grid View"
							ViewMode.GRID -> "Switch to List View"
						}
						Icon(
							icon,
							modifier = Modifier.size(SplitButtonDefaults.LeadingIconSize),
							contentDescription = contentDesc,
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
							onCheckedChange = { onCheckedChange(it) },
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
				onDismissRequest = { onCheckedChange(false) },
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
									onMenuItemChange(itemIndex)
									onCheckedChange(false)
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
}