package com.gnexus.app.ui.screens.library.motion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue

class PanePhysicsController {
	var progress by mutableFloatStateOf(0f)
		private set

	fun update(v: Float) {
		progress = v.coerceIn(0f, 1f)
	}

	fun reset() {
		progress = 0f
	}
}