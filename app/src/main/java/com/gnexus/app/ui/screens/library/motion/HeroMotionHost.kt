package com.gnexus.app.ui.screens.library.motion

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

@Composable
fun HeroMotionHost(
	heroState: HeroState,
	content: @Composable (Float) -> Unit
) {
	val progress by animateFloatAsState(
		heroState.progress,
		animationSpec = spring(
			dampingRatio = 0.82f,
			stiffness = Spring.StiffnessLow
		)
	)

	content(progress)
}