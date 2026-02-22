package com.gnexus.app.ui.screens.library

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable

@Composable
fun LibraryRoute(
	windowSizeClass: WindowSizeClass,
//	viewModel: LibraryViewModel = hiltViewModel()

) {
//	val uiState by viewModel.uiState.collectAsState()

	LibraryScreen(
		windowSizeClass = windowSizeClass,
//		uiState = uiState,
//		onAction = viewModel::onAction,
	)
}
