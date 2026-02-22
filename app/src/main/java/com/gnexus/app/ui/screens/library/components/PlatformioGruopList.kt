package com.gnexus.app.ui.screens.library.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gnexus.app.R

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
fun PlatformGroupList() {
	val options = listOf("PlayStation", "Xbox", "Steam", "NintendoSwitch", "EpicGames")
	val icons: List<Int> = listOf(
		R.drawable.playstation,
		R.drawable.xbox,
		R.drawable.steam,
		R.drawable.nintendo_switch,
		R.drawable.epicgames
	)
	val checked = remember { mutableStateListOf(true, false, false, false, false) }


	Surface(
		modifier = Modifier
			.fillMaxWidth(),
		shape = RoundedCornerShape(topStart = 24.dp),
		color = MaterialTheme.colorScheme.surfaceContainerHighest,
		tonalElevation = 2.dp
	) {
		Box {
			Box(
				Modifier
					.matchParentSize()
					.blur(50.dp)
			)
			Row(
				modifier = Modifier
					.padding(6.dp)
					.fillMaxWidth(),
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.Absolute.Center,
			) {
				options.forEachIndexed { index, label ->
					ToggleButton(
						modifier = Modifier.padding(start = 1.dp),
						checked = checked[index],
						onCheckedChange = { checked[index] = it },
						shapes =
							when (index) {
								0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
								options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
								else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
							},
					) {
						Image(
							painter = painterResource(id = icons[index]),
							contentDescription = null,
							modifier = Modifier.size(20.dp),
							colorFilter = if (checked[index]) ColorFilter.tint(
								MaterialTheme.colorScheme.background
							) else ColorFilter.tint(
								MaterialTheme.colorScheme.onBackground
							),
						)
					}
				}
			}
		}
	}
}