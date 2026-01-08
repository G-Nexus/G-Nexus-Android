package com.gnexus.app.ui.screens.library

import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gnexus.app.domain.model.MetaGame
import com.gnexus.app.ui.screens.library.panes.GameFeedPane
import com.gnexus.app.ui.screens.library.panes.TrophyGroupsPane
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun LibraryScreen(
    windowSizeClass: WindowSizeClass,
    viewModel: LibraryViewModel = viewModel()
) {
    val navigator = rememberSupportingPaneScaffoldNavigator<MetaGame>()
    val scope = rememberCoroutineScope()

    BackHandler(navigator.canNavigateBack()) {
        scope.launch {
            navigator.navigateBack()
        }
    }

    SupportingPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        mainPane = {
            AnimatedPane{
                GameFeedPane()
            }

        },
        supportingPane = {
            AnimatedPane {
//                when(val dest = navigator.currentDestination) {
//
//                }
                TrophyGroupsPane()
            }
        }
    )

}


/**
 * 定义库模块内的导航目的地
 */
//sealed class LibraryDestination : Parcelable {
//    @Parcelize data class Groups(val game: MetaGame) : LibraryDestination()
//    @Parcelize data class TrophyList(val game: MetaGame, val group: TrophyGroup) : LibraryDestination()
//    @Parcelize data class Detail(val game: MetaGame, val group: TrophyGroup, val trophy: Trophy) : LibraryDestination()
//}