package com.gnexus.app.ui.screens.library.motion

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
class HeroState {
	var progress by mutableFloatStateOf(0f)
		private set

	var activeId: Int? by mutableStateOf(null)
		private set

	fun start(id: Int) {
		activeId = id
		progress = 0f
	}

	fun update(v: Float) {
		progress = v.coerceIn(0f, 1f)
	}

	fun finish() {
		progress = 1f
	}

	fun reset() {
		activeId = null
		progress = 0f
	}
}