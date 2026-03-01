package com.gnexus.app.ui.screens.library.panes

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@SuppressLint("FrequentlyChangingValue")
@Composable
fun GameDetailPane(
	heroProgress: Float = 0f
) {
	rememberLazyListState()
	Column(
		Modifier.background(
			MaterialTheme.colorScheme.error
		)
	) {

		Box(
			Modifier
				.fillMaxWidth()
				.height(240.dp)
				.graphicsLayer {

					scaleX = 0.85f + heroProgress * 0.15f
					scaleY = 0.85f + heroProgress * 0.15f
				}
				.background(MaterialTheme.colorScheme.primaryContainer)
		)

		Text(
			"Game Detail",
			modifier = Modifier.padding(16.dp)
		)
	}
}
