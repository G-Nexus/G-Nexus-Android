package com.gnexus.app.ui.screens.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun PlatformSummarySkeleton() {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 24.dp, vertical = 8.dp)
			.height(50.dp)
			.shimmer(), // 应用闪烁动画
		verticalAlignment = Alignment.CenterVertically
	) {
		// 头像占位符
		Box(
			modifier = Modifier
				.size(42.dp)
				.clip(CircleShape)
				.background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
		)
		Spacer(modifier = Modifier.width(8.dp))
		// 左侧文字占位符
		Column(
			modifier = Modifier.height(40.dp),
			verticalArrangement = Arrangement.SpaceAround
		) {
			Box(
				modifier = Modifier
					.height(14.dp)
					.width(80.dp)
					.background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
			)
			Box(
				modifier = Modifier
					.height(12.dp)
					.width(120.dp)
					.background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
			)
		}
		Spacer(Modifier.weight(1f))
		// 右侧文字占位符
		Column(
			modifier = Modifier.height(40.dp),
			horizontalAlignment = Alignment.End,
			verticalArrangement = Arrangement.SpaceAround
		) {
			Box(
				modifier = Modifier
					.height(12.dp)
					.width(70.dp)
					.background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
			)
			Box(
				modifier = Modifier
					.height(12.dp)
					.width(60.dp)
					.background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
			)
		}
	}
}