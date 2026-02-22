package com.gnexus.app.ui.screens.library

import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.PaneScaffoldDirective
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.gnexus.app.ui.screens.library.navigation.LibraryDestination
import com.gnexus.app.ui.screens.library.panes.GameDetailPane
import com.gnexus.app.ui.screens.library.panes.GameFeedPane
import com.gnexus.app.ui.screens.library.panes.TrophyGroupsPane
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun LibraryScreen(
	windowSizeClass: WindowSizeClass,
//	uiState: LibraryUiState,
//	onAction: (LibraryAction) -> Unit
) {
	val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
	val scope = rememberCoroutineScope()

	val customDirective = PaneScaffoldDirective(
		maxHorizontalPartitions = if (isCompact) 1 else 2,
		horizontalPartitionSpacerSize = 16.dp,
		maxVerticalPartitions = 1,
		verticalPartitionSpacerSize = 16.dp,
		defaultPanePreferredWidth = 360.dp,
		excludedBounds = emptyList()
	)

	val navigator = rememberSupportingPaneScaffoldNavigator<LibraryDestination>(
		scaffoldDirective = customDirective
	)

	// 只有在真正有新内容时才更新，防止在导航回退过程中意外覆盖
	// 在 LibraryScreen 内部增加一个辅助标志位
	val isRestoring = rememberSaveable { mutableStateOf(true) }
	// 1. 强化状态锁：使用 DerivedStateOf 减少不必要的重组，避免“切回卡顿”

	// 使用一个更稳定的方式来同步 navigator 和你的本地锁
	val currentDest = navigator.currentDestination?.contentKey
	var lastValidDest by rememberSaveable { mutableStateOf<LibraryDestination?>(null) }

	// 核心逻辑：确保即使在 NavHost 切换时，内容也是锁死的
	LaunchedEffect(currentDest) {
		if (currentDest != null) {
			lastValidDest = currentDest
			isRestoring.value = false
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
				// 技巧：在小屏模式下，如果 SupportingPane 正在显示，
				// 我们通过 Modifier.alpha(0f) 而不是直接移除内容，
				// 这样切回时内容是“预加载”好的，不会卡顿。
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
				)
			}
		},
		supportingPane = {
			AnimatedPane {
				// 核心改动：大屏直接用 current，小屏才用锁
				val displayDest = if (isCompact) {
					currentDest ?: lastValidDest
				} else {
					// 大屏模式下，如果 navigator 还没恢复，显示 null（即空状态或占位）
					// 这样系统不需要在切回瞬间去计算一个“假”的历史内容，减少掉帧
					currentDest
				}

				if (displayDest != null) {
					when (displayDest) {
						is LibraryDestination.Trophy -> TrophyGroupsPane(displayDest.game)
						is LibraryDestination.Guide -> TrophyGroupsPane(displayDest.game)
						is LibraryDestination.GameInfo -> GameDetailPane()
					}
				}
			}
		}
	)
}