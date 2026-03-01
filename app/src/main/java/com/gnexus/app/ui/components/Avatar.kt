package com.gnexus.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun Avatar(avatarUrl: String, modifier: Modifier = Modifier) {
	AsyncImage(
		model = avatarUrl,
		modifier = modifier
			.size(40.dp)
			.clip(CircleShape)
			.background(MaterialTheme.colorScheme.background)
			.border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
		contentScale = ContentScale.Crop,
		contentDescription = "Avatar"
	)
}