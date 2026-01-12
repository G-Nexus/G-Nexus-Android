package com.gnexus.app.ui.screens.library.panes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

//game: MetaGame, onGroupClick: (TrophyGroup) -> Unit
@Composable
fun TrophyGroupsPane() {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(Modifier.height(200.dp)) {
            AsyncImage(
                model = "https://eldenring.wiki.gg/images/e/ec/ELDENRING_01_4K.jpg",
                contentDescription = null,
                alignment = Alignment.BottomCenter,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
            )
            Column(
                Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text("Elden Ring", style = MaterialTheme.typography.headlineSmall, color = Color.White)
                Text(
                    "FromSoftware",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(0.7f)
                )
            }
        }

        Text("奖杯组", Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
    }
}