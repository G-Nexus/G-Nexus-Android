package com.gnexus.app.ui.screens.library.state

sealed interface LibraryAction {
	data class OpenDetail(val id: Int) : LibraryAction
	data class OpenAchievement(val id: Int) : LibraryAction
	data object ClosePane : LibraryAction
}