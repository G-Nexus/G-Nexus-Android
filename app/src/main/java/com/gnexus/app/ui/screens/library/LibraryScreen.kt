package com.gnexus.app.ui.screens.library

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.PaneScaffoldDirective
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.material3.rememberContainedSearchBarState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.gnexus.app.ui.screens.library.components.LibraryTopAppBar
import com.gnexus.app.ui.screens.library.navigation.LibraryDestination
import com.gnexus.app.ui.screens.library.navigation.PlatformDestination
import com.gnexus.app.ui.screens.library.panes.GameDetailPane
import com.gnexus.app.ui.screens.library.panes.GameFeedPane
import com.gnexus.app.ui.screens.library.panes.PlatformDetailPane
import com.gnexus.app.ui.screens.library.panes.TrophyGroupsPane
import kotlinx.coroutines.launch

enum class ViewMode {
	LIST,
	GRID
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
	windowSizeClass: WindowSizeClass,
) {
	val viewModel: LibraryViewModel = hiltViewModel()

	val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
	val scope = rememberCoroutineScope()

	// --- Navigator 和 TopAppBar 的状态 ---
	val navigator = rememberSupportingPaneScaffoldNavigator<LibraryDestination>(
		scaffoldDirective = PaneScaffoldDirective(
			maxHorizontalPartitions = if (isCompact) 1 else 2,
			horizontalPartitionSpacerSize = 16.dp,
			maxVerticalPartitions = 1,
			verticalPartitionSpacerSize = 16.dp,
			defaultPanePreferredWidth = 360.dp,
			excludedBounds = emptyList()
		)
	)
	val searchBarState = rememberContainedSearchBarState()
	var checked by remember { mutableStateOf(false) }
	var checkedMenuItem by remember { mutableIntStateOf(0) }

	var hasUserInteractedWithSupportingPane by rememberSaveable { mutableStateOf(false) }

	var viewMode by rememberSaveable { mutableStateOf(ViewMode.LIST) } // 默认是列表视图

	// ---  在大屏模式下，为 supportingPane 设置一个默认内容 ---
	//     这个 LaunchedEffect 只会在 LibraryScreen 首次组合时运行
	LaunchedEffect(isCompact, navigator, hasUserInteractedWithSupportingPane) {
		if (!isCompact && !navigator.canNavigateBack() && !hasUserInteractedWithSupportingPane) {
			navigator.navigateTo(
				SupportingPaneScaffoldRole.Supporting,
				LibraryDestination.PlatformDetail(PlatformDestination.PSN) // 默认显示PSN平台详情
			)
		}
	}

	BackHandler(enabled = navigator.canNavigateBack()) {
		scope.launch { navigator.navigateBack() }
	}

	SupportingPaneScaffold(
		directive = navigator.scaffoldDirective,
		value = navigator.scaffoldValue,
		mainPane = {
			AnimatedPane {
				Scaffold(
					topBar = {
						LibraryTopAppBar(
							searchBarState = searchBarState,
							checked = checked,
							onCheckedChange = { checked = it },
							checkedMenuItem = checkedMenuItem,
							onMenuItemChange = {
								checkedMenuItem = it
								viewModel.onSortOrderChanged(it)
							},
							currentViewMode = viewMode,
							onViewModeChange = {
								viewMode =
									if (viewMode == ViewMode.LIST) ViewMode.GRID else ViewMode.LIST
							}
						)
					}
				) { innerPadding ->
					Box(modifier = Modifier.padding(innerPadding)) {
						GameFeedPane(
							windowSizeClass = windowSizeClass,
							onGameClick = { game ->
								scope.launch {
									navigator.navigateTo(
										SupportingPaneScaffoldRole.Supporting,
										LibraryDestination.GameInfo(game)
									)
								}
							},
							onTrophyClick = { game ->
								scope.launch {
									navigator.navigateTo(
										SupportingPaneScaffoldRole.Supporting,
										LibraryDestination.Trophy(game)
									)
								}
							},
							onGuideClick = { game ->
								scope.launch {
									navigator.navigateTo(
										SupportingPaneScaffoldRole.Supporting,
										LibraryDestination.Guide(game)
									)
								}
							},
							onPlatformChange = { platformDestination ->
								// 如果是大屏，并且当前显示的不是游戏详情（即仍在显示平台详情），
								// 则同步更新平台详情页。
								val currentContent = navigator.currentDestination?.contentKey
								if (!isCompact && (!hasUserInteractedWithSupportingPane || currentContent is LibraryDestination.PlatformDetail?)) {
									scope.launch {
										navigator.navigateTo(
											SupportingPaneScaffoldRole.Supporting,
											LibraryDestination.PlatformDetail(platformDestination)
										)
									}
								}
							},
							onPlatformClick = { platformDestination ->
								hasUserInteractedWithSupportingPane = true
								scope.launch {
									navigator.navigateTo(
										SupportingPaneScaffoldRole.Supporting,
										LibraryDestination.PlatformDetail(platformDestination)
									)
								}
							},
							viewMode = viewMode,
						)
					}
				}
			}
		},
		supportingPane = {
			AnimatedPane {
				val displayDest = navigator.currentDestination?.contentKey
				if (displayDest != null) {
					// --- 更新 when 语句以处理所有 Destination 类型 ---
					when (displayDest) {
						is LibraryDestination.GameInfo -> GameDetailPane()
						is LibraryDestination.Trophy -> TrophyGroupsPane(displayDest.game)
						is LibraryDestination.Guide -> TrophyGroupsPane(displayDest.game)
						is LibraryDestination.PlatformDetail -> PlatformDetailPane(displayDest.platform)
					}
				}
			}
		}
	)
}