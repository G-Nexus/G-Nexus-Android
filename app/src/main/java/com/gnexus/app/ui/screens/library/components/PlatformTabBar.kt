package com.gnexus.app.ui.screens.library.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gnexus.app.R

enum class PlatformTab {
	PlayStation, Xbox, Steam
}

@Composable
fun PlatformTabBar(
	selected: PlatformTab,
	onSelected: (PlatformTab) -> Unit
) {

	@OptIn(ExperimentalMaterial3ExpressiveApi::class)
	@Composable
	fun PlatformGroupList(
		selected: PlatformTab,
		onSelected: (PlatformTab) -> Unit
	) {

		Surface(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 12.dp),
			shape = RoundedCornerShape(28.dp),
			tonalElevation = 6.dp,
			shadowElevation = 8.dp,
			color = MaterialTheme.colorScheme.surfaceContainerHigh
		) {

			Row(
				modifier = Modifier
					.padding(6.dp)
					.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceEvenly
			) {

				PlatformTab.entries.forEach { tab ->

					val isSelected = tab == selected

					Surface(
						onClick = { onSelected(tab) },
						shape = RoundedCornerShape(20.dp),
						color = if (isSelected)
							MaterialTheme.colorScheme.primary
						else Color.Transparent,
						tonalElevation = if (isSelected) 4.dp else 0.dp,
						modifier = Modifier
							.weight(1f)
							.animateContentSize()
					) {

						Icon(
							painter = painterResource(R.drawable.xbox),
							contentDescription = "xbox",
							tint = if (isSelected)
								MaterialTheme.colorScheme.onPrimary
							else MaterialTheme.colorScheme.onSurfaceVariant,
							modifier = Modifier
								.padding(vertical = 10.dp)
								.size(20.dp)
						)
					}
				}
			}
		}
	}

}
