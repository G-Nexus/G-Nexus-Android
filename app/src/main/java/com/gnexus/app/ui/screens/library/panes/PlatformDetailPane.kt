package com.gnexus.app.ui.screens.library.panes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PlatformDetailPane(destination: Destination) {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		// 这里可以放更复杂的UI，比如平台的Logo、统计信息等
		Text(text = "这里是 ${destination.label} 平台的详情页")
	}
}