package com.gnexus.app.ui.screens.library.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.gnexus.app.R
import com.gnexus.app.data.db.GameEntity

@Composable
fun GameCard(
	game: GameEntity?,
	onGameClick: (Int) -> Unit,
) {
	val interactionSource = remember { MutableInteractionSource() }
	val pressed by interactionSource.collectIsPressedAsState()

	ElevatedCard(
		onClick = {
			onGameClick(1)
		},
		modifier = Modifier
			.fillMaxWidth()
			.padding(8.dp)
			.graphicsLayer {
				scaleX = if (pressed) 0.98f else 1f
				scaleY = if (pressed) 0.98f else 1f
			}
			.clip(RoundedCornerShape(12.dp))
			.clickable(
				interactionSource = interactionSource,
				indication = null
			) {
				onGameClick(1)
			},
		shape = RoundedCornerShape(12.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceContainer
		)
	) {
		Box {
			AsyncImage(
				model = game?.imageUrl,
				contentDescription = game?.name,
				modifier = Modifier
					.fillMaxSize()
					.height(100.dp)
			)
			Box(
				modifier = Modifier
					.align(Alignment.BottomEnd)
					.padding(8.dp)
			) {
				Image(
					painter = painterResource(R.drawable.ps5_category),
					contentDescription = "GamePass",
				)
			}
			Box(
				modifier = Modifier
					.align(Alignment.BottomStart)
					.padding(6.dp)
			) {
				Image(
					painter = painterResource(R.drawable.psn),
					contentDescription = "GamePass",
					modifier = Modifier.size(18.dp),
				)
			}
		}
	}
}