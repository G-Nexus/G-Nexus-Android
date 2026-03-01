package com.gnexus.app.ui.screens.library.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class LibraryDestination : Parcelable {
	@Parcelize
	data class GameInfo(val game: Int) : LibraryDestination()

	@Parcelize
	data class Trophy(val game: Int) : LibraryDestination()

	@Parcelize
	data class Guide(val game: Int) : LibraryDestination()

	@Parcelize
	data class PlatformDetail(val platform: PlatformDestination) : LibraryDestination()
}
