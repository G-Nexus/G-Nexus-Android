package com.gnexus.app.ui.screens.library.state

sealed interface LibraryPaneState {
	data object None : LibraryPaneState
	data class Detail(val gameId: Int) : LibraryPaneState
	data class Achievement(val gameId: Int) : LibraryPaneState
}