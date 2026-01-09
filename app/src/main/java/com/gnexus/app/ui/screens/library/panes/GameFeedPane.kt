package com.gnexus.app.ui.screens.library.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.gnexus.app.ui.screens.library.LibraryPreviewData.mockGame

@Composable
fun GameFeedPane() {
    val mockData = Array(3) { mockGame }

    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(mockData) { game ->
            AsyncImage(
                modifier = Modifier.clip(CircleShape),
                model = "https://imgs.ali213.net/oday/uploadfile/2022/12/30/20221230122222799.jpg",
                contentDescription = null,
            )
        }
    }
}
