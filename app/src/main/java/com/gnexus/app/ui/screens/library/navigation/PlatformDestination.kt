package com.gnexus.app.ui.screens.library.navigation

import com.gnexus.app.R

enum class PlatformDestination(
	val route: String,
	val label: String,
	val icon: Int,
	val contentDescription: String,
	val gamePass: Int? = null
) {
	PSN("psn", "PSN", R.drawable.playstation, "PlayStation", R.drawable.psn),
	STEAM("steam", "Steam", R.drawable.steam, "Steam"),
	XBOX("xbox", "Xbox", R.drawable.xbox, "Xbox", R.drawable.game_pass),
	NS("NS", "NS", R.drawable.nintendo_switch, "Nintendo Switch"),
	EPIC("EPIC", "Epic", R.drawable.epicgames, "Epic Games")
}