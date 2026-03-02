package com.gnexus.app.ui.screens.library.components

import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gnexus.app.ui.components.Avatar
import com.gnexus.app.ui.screens.library.navigation.PlatformDestination
import android.graphics.RenderEffect as AndroidRenderEffect

@Composable
fun PlatformSummaryHeader(
	pageIndex: Int,
	onPlatformClick: (PlatformDestination) -> Unit,
	gameCount: Int
) {
	val blurModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
		Modifier.graphicsLayer(
			renderEffect = AndroidRenderEffect.createBlurEffect(
				20f, // X轴模糊半径
				20f, // Y轴模糊半径
				Shader.TileMode.CLAMP
			).asComposeRenderEffect()
		)
	} else {
		Modifier // 在低版本上，返回一个空的Modifier，不应用任何效果
	}
	Box(
		modifier = Modifier
			.padding(start = 24.dp, top = 8.dp, end = 24.dp)
			.clip(MaterialTheme.shapes.medium)
			.clickable { onPlatformClick(PlatformDestination.entries[pageIndex]) },
	) {

		Surface(
			modifier = Modifier
				.matchParentSize() // 让背景层的大小与Box完全一致
				.then(blurModifier), // 将模糊效果应用在这一层
			shape = MaterialTheme.shapes.medium,
			// 使用半透明颜色作为背景，在所有版本上都能提供“玻璃”质感
			color = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.75f),
			// (可选) 添加淡淡的边框来增强质感
			border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
			content = {} // 这个 Surface 内部是空的
		)
		Row(
			modifier = Modifier
				.padding(horizontal = 24.dp, vertical = 8.dp)
				.height(50.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Avatar(
				"https://psn-rsc.prod.dl.playstation.net/psn-rsc/avatar/HP9000/PPSA01971_00-DEATHSDCAVATAR11_4E1229519D7858373038_xl.png",
				Modifier.size(42.dp)
			)
			Column(
				modifier = Modifier.padding(start = 8.dp)
			) {
				Row(
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = "FujiCat",
						fontSize = MaterialTheme.typography.bodySmall.fontSize,
						modifier = Modifier
							.padding(end = 8.dp)
					)

					Image(
						painter = painterResource(PlatformDestination.PSN.gamePass as Int),
						contentDescription = "Subscription Status",
						modifier = Modifier.size(15.dp),
					)
				}
				Text(
					"jonas-lang-garfi",
					fontSize = MaterialTheme.typography.bodySmall.fontSize
				)
			}
			Spacer(Modifier.weight(1f))
			Column(
				horizontalAlignment = Alignment.End
			) {
				Text(
					"$gameCount 款游戏",
					fontSize = MaterialTheme.typography.bodySmall.fontSize
				)
				Text("2000 个奖杯", fontSize = MaterialTheme.typography.bodySmall.fontSize)
			}
		}
	}
}