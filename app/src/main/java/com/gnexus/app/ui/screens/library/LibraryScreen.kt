package com.gnexus.app.ui.screens.library

// 确保您已经创建并导入了这些 Pane
//import com.gnexus.app.ui.screens.library.panes.GameGuidePane
//import com.gnexus.app.ui.screens.library.panes.GameInfoPane
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
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
    val navigator = rememberSupportingPaneScaffoldNavigator<LibraryDestination>()
    val scope = rememberCoroutineScope()
    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    // 小屏幕 overlay 控制
    var overlayDestination by remember { mutableStateOf<LibraryDestination?>(null) }
    var clickBounds by remember { mutableStateOf<androidx.compose.ui.geometry.Rect?>(null) }

    BackHandler(
        enabled = overlayDestination != null || navigator.canNavigateBack()
    ) {
        scope.launch {
            if (overlayDestination != null) {
                overlayDestination = null
            } else {
                navigator.navigateBack()
            }
        }
    }

    SupportingPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        mainPane = {
            AnimatedPane {
                GameFeedPane(
                    windowSizeClass = windowSizeClass,
                    onGameClick = { game, coordinates ->
                        if (isCompact) {
                            overlayDestination = LibraryDestination.GameInfo(game)
                            clickBounds = coordinates
                        } else {
                            scope.launch {
                                navigator.navigateTo(
                                    SupportingPaneScaffoldRole.Supporting,
                                    LibraryDestination.GameInfo(game)
                                )
                            }
                        }
                    },
                    onTrophyClick = { game, coordinates ->
                        scope.launch {
                            if (isCompact) {
                                overlayDestination = LibraryDestination.Trophy(game)
                                clickBounds = coordinates
                            } else {
                                navigator.navigateTo(
                                    SupportingPaneScaffoldRole.Supporting,
                                    LibraryDestination.Trophy(game)
                                )
                            }
                        }
                    },
                    onGuideClick = { game, coordinates ->
                        if (isCompact) {
                            overlayDestination = LibraryDestination.Trophy(game)
                        } else {
                            scope.launch {
                                navigator.navigateTo(
                                    SupportingPaneScaffoldRole.Supporting,
                                    LibraryDestination.Guide(game)
                                )
                            }
                        }
                    }
                )

            }

        },
        supportingPane = {
            if (!isCompact) {
                AnimatedPane {
                    when (val destination = navigator.currentDestination?.contentKey) {
                        is LibraryDestination.Trophy -> TrophyGroupsPane(destination.game)
                        is LibraryDestination.Guide -> TrophyGroupsPane(destination.game)
                        is LibraryDestination.GameInfo -> TrophyGroupsPane(destination.game)
                        null -> {}
                    }
                }
            }
        },
    )

    // 小屏幕覆盖显示
    if (isCompact && overlayDestination != null && clickBounds != null) {
        val scaleXAnim = remember { Animatable(0f) }
        val scaleYAnim = remember { Animatable(0f) }
        val offsetXAnim = remember { Animatable(clickBounds!!.left) }
        val offsetYAnim = remember { Animatable(clickBounds!!.top) }


        LaunchedEffect(overlayDestination) {
            launch {
                offsetXAnim.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            }
            launch {
                offsetYAnim.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            }
            launch {
                scaleXAnim.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            }
            launch {
                scaleYAnim.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    translationX = offsetXAnim.value,
                    translationY = offsetYAnim.value,
                    scaleX = scaleXAnim.value,
                    scaleY = scaleYAnim.value
                )
                .background(MaterialTheme.colorScheme.surface)
        ) {
            when (val destination = overlayDestination) {
                is LibraryDestination.GameInfo -> TrophyGroupsPane(destination.game)
                is LibraryDestination.Trophy -> TrophyGroupsPane(destination.game)
                is LibraryDestination.Guide -> TrophyGroupsPane(destination.game)
                else -> {}
            }
        }
    }
}