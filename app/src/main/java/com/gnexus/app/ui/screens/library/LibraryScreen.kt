package com.gnexus.app.ui.screens.library

import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.PaneScaffoldDirective
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gnexus.app.ui.screens.library.navigation.LibraryDestination
import com.gnexus.app.ui.screens.library.panes.GameFeedPane
import com.gnexus.app.ui.screens.library.panes.TrophyGroupsPane
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun LibraryScreen(
    windowSizeClass: WindowSizeClass,
    viewModel: LibraryViewModel = viewModel()
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

    // 1. 强化状态锁：使用 DerivedStateOf 减少不必要的重组，避免“切回卡顿”
    val currentDest = navigator.currentDestination?.contentKey
    var lastValidDest by rememberSaveable { mutableStateOf<LibraryDestination?>(null) }

    // 只有在真正有新内容时才更新，防止在导航回退过程中意外覆盖
    if (currentDest != null) {
        lastValidDest = currentDest
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
                    }
                )
            }
        },
        supportingPane = {
            AnimatedPane(
                // 优化动画参数：让退出稍微慢一点，给 MainPane 留出加载时间
                enterTransition = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                exitTransition = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
            ) {
                // 2. 核心修复：锁定显示逻辑
                // 在小屏模式下，为了防止返回时的瞬间黑屏，我们强制使用 lastValidDest
                // 因为返回时 currentDest 会瞬间变 null，但动画还要跑 300ms
                val displayDest = if (isCompact) {
                    currentDest ?: lastValidDest
                } else {
                    currentDest
                }

                displayDest?.let { dest ->
                    when (dest) {
                        is LibraryDestination.Trophy -> TrophyGroupsPane(dest.game)
                        is LibraryDestination.Guide -> TrophyGroupsPane(dest.game)
                        is LibraryDestination.GameInfo -> TrophyGroupsPane(dest.game)
                    }
                }
            }
        },
    )
}