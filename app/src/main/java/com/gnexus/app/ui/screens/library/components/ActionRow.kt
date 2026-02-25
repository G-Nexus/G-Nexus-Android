package com.gnexus.app.ui.screens.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ActionRow(
	onGuideClick: (Int) -> Unit,
	icon: ImageVector,
	text: String
) {
	val interactionSource = remember { MutableInteractionSource() }
	val pressed by interactionSource.collectIsPressedAsState()

	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clip(RoundedCornerShape(16.dp))
			.background(
				if (pressed)
					MaterialTheme.colorScheme.surfaceContainerHigh
				else
					Color.Transparent
			)
			.padding(horizontal = 12.dp, vertical = 10.dp)
			.clickable(
				interactionSource = interactionSource,
				indication = null
			) {
				onGuideClick(1)
			},
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(
			icon,
			contentDescription = null,
			tint = MaterialTheme.colorScheme.primary
		)
		Spacer(Modifier.width(20.dp))
		Text(
			text,
			style = MaterialTheme.typography.bodyMedium
		)
		Spacer(Modifier.weight(1f))
		Icon(
			Icons.AutoMirrored.Outlined.ArrowForwardIos,
			contentDescription = null,
			tint = MaterialTheme.colorScheme.outlineVariant
		)
	}
}
