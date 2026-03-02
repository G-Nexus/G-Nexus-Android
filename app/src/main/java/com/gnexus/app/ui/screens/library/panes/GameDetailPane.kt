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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.gnexus.app.ui.screens.library.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("FrequentlyChangingValue")
@Composable
fun GameDetailPane(
	gameId: String,
	viewModel: LibraryViewModel = hiltViewModel()
) {
	val game by viewModel.getGameDetails(gameId).collectAsState(initial = null)

	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				navigationIcon = {
					IconButton(onClick = {}) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, "Navigate back")
					}
				},
				title = { Text(game?.localizedName ?: "") },
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
