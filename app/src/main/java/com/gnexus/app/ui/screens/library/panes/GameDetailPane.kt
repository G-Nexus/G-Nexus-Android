package com.gnexus.app.ui.screens.library.panes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("FrequentlyChangingValue")
@Composable
fun GameDetailPane(
) {
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				navigationIcon = {
					IconButton(onClick = {}) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, "Navigate back")
					}
				},
				title = { Text("Game Detail") },
				actions = {
					IconButton(onClick = {}) {
						Icon(
							Icons.Filled.Refresh,
							contentDescription = "Refresh game info"
						)
					}
				}
			)
		}
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(paddingValues)
		) {
			Text("GameDetailPane")
		}
	}
}
