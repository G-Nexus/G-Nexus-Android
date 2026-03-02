package com.gnexus.app.ui.screens.library.panes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gnexus.app.ui.screens.library.navigation.PlatformDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlatformDetailPane(destination: PlatformDestination) {
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				navigationIcon = {
					IconButton(onClick = {}) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, "Navigate back")
					}
				},
				title = { Text(destination.label) },
				actions = {
					IconButton(onClick = {}) {
						Icon(
							Icons.Filled.Refresh,
							contentDescription = "Refresh ${destination.label} info"
						)
					}
				}
			)
		}
	) { paddingValues ->
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(paddingValues)
		) {
			Text(
				"PlatformDetailPane",
				modifier = Modifier.align(Alignment.Center)
			)
		}
	}
}