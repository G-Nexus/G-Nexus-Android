package com.gnexus.app.ui.screens.library.panes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.gnexus.app.ui.screens.library.LibraryPreviewData.mockGame

@Composable
fun TrophyListPane() {
    val mockData = Array(3) { mockGame }

    Column {
        LazyColumn {
            items(mockData) { game ->
                Text(game.title)
            }
        }
    }
}