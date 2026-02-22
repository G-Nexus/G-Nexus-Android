package com.gnexus.app.ui.screens.library.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp

@Composable
fun StickyBlurHeader(
	scroll: Int,
	content: @Composable () -> Unit
) {

	val p = (scroll / 300f).coerceIn(0f, 1f)

	Surface(
		modifier = Modifier
			.fillMaxWidth()
			.statusBarsPadding()
			.blur((p * 20).dp),
		tonalElevation = (p * 8).dp,
		color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
	) {
		Box(Modifier.padding(12.dp)) {
			content()
		}
	}
}
