package com.gnexus.app.ui.screens.library.components

import android.graphics.Shader
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gnexus.app.ui.components.Avatar
import com.gnexus.app.ui.screens.library.model.PlatformSummary
import com.gnexus.app.ui.screens.library.navigation.PlatformDestination
import android.graphics.RenderEffect as AndroidRenderEffect

/**
 * 显示平台概要信息的悬浮Header
 *
 * @param platform 从ViewModel获取的平台概要数据。
 * @param gameCount 从Paging获取的当前列表的游戏总数。
 * @param onPlatformClick 点击Header时触发的回调。
 * @param platformDestination 当前Header代表的平台目标，用于点击回调。
 */
@Composable
fun PlatformSummaryHeader(
	platform: PlatformSummary,
	gameCount: Int,
	onPlatformClick: (PlatformDestination) -> Unit,
	platformDestination: PlatformDestination,
) {
	val blurModifier =
		Modifier.graphicsLayer(
			renderEffect = AndroidRenderEffect.createBlurEffect(
				20f,
				20f,
				Shader.TileMode.CLAMP
			).asComposeRenderEffect()
		)
	Box(
		modifier = Modifier
			.padding(start = 24.dp, top = 8.dp, end = 24.dp)
			.clip(MaterialTheme.shapes.medium)
			.clickable { onPlatformClick(platformDestination) },
	) {

		Surface(
			modifier = Modifier
				.matchParentSize()
				.then(blurModifier),
			shape = MaterialTheme.shapes.medium,
			color = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.75f),
			border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)),
			content = {}
		)
		Row(
			modifier = Modifier
				.padding(horizontal = 24.dp, vertical = 8.dp)
				.height(50.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Avatar(
				platform.avatarUrl,
				Modifier.size(42.dp)
			)
			Column(
				modifier = Modifier.padding(start = 8.dp)
			) {
				Row(
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = platform.userName,
						style = MaterialTheme.typography.titleSmall,
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
					text = platform.onlineId,
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
			}
			Spacer(Modifier.weight(1f))
			Column(
				horizontalAlignment = Alignment.End
			) {
				Text(
					"$gameCount 款游戏",
					style = MaterialTheme.typography.bodySmall
				)
				Text("${platform.trophyCount} 个奖杯", style = MaterialTheme.typography.bodySmall)
			}
		}
	}
}