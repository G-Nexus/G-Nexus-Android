package com.gnexus.app.ui.screens.library.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gnexus.app.ui.components.Avatar
import com.gnexus.app.ui.screens.library.navigation.PlatformDestination

@Composable
fun PlatformSummaryHeader(
	pageIndex: Int,
	onPlatformClick: (PlatformDestination) -> Unit,
	gameCount: Int
) {
	Surface(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 24.dp, vertical = 8.dp)
			.clip(MaterialTheme.shapes.medium)
			.clickable {
				onPlatformClick(PlatformDestination.entries[pageIndex])
			},
		shape = MaterialTheme.shapes.medium,
		color = MaterialTheme.colorScheme.surfaceContainer
	) {
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
						contentDescription = "GamePass",
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