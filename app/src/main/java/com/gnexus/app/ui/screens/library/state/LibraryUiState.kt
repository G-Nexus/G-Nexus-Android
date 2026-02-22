package com.gnexus.app.ui.screens.library.state

import com.gnexus.app.ui.screens.library.model.GameUiModel

data class LibraryUiState(
	val games: List<GameUiModel> = emptyList(),
	val currentPane: LibraryPaneState = LibraryPaneState.None
)