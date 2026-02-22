package com.gnexus.app.ui.screens.library.motion

import androidx.activity.compose.PredictiveBackHandler
import androidx.compose.runtime.Composable

@Composable
fun PanePredictiveBack(
	enabled: Boolean,
	heroState: HeroState,
	controller: PanePhysicsController,
	onBack: () -> Unit
) {
	PredictiveBackHandler(enabled) { flow ->
		flow.collect { event ->
			controller.update(event.progress)
			heroState.update(1f - event.progress)

			if (event.progress >= 1f) {
				onBack()
				controller.reset()
				heroState.reset()
			}
		}
	}
}