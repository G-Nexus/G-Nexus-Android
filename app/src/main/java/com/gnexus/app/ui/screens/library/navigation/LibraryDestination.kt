package com.gnexus.app.ui.screens.library.navigation

sealed interface LibraryDestination {
    data class GameInfo(val game: Int) : LibraryDestination
    data class Trophy(val game: Int) : LibraryDestination
    data class Guide(val game: Int) : LibraryDestination
}