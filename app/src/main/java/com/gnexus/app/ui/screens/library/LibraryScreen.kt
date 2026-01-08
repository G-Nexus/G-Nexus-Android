package com.gnexus.app.ui.screens.library

import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowSizeClass
import com.gnexus.app.domain.model.MetaGame
import kotlinx.coroutines.launch
import androidx.compose.material3.adaptive.layout.AnimatedPane

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

//    SupportingPaneScaffold(
//        directive = navigator.scaffoldDirective,
//        value = navigator.scaffoldValue,
//        mainPane = {
//            AnimatedPane {
//
//            }
//        }
//    )

}