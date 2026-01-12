package com.gnexus.app.ui.screens.library.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Work
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.gnexus.app.ui.screens.library.LibraryPreviewData.mockGame
import com.gnexus.app.ui.screens.library.components.GameLibraryCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GameFeedPane() {
    val mockData = Array(20) { mockGame }
    TextFieldState("")

    val options = listOf("Work", "Restaurant", "Coffee", "Search", "Home")
    val unCheckedIcons =
        listOf(
            Icons.Outlined.Work,
            Icons.Outlined.Restaurant,
            Icons.Outlined.Coffee,
            Icons.Outlined.Search,
            Icons.Outlined.Home,
        )
    val checkedIcons =
        listOf(
            Icons.Filled.Work,
            Icons.Filled.Restaurant,
            Icons.Filled.Coffee,
            Icons.Filled.Search,
            Icons.Filled.Home,
        )
    val checked = remember { mutableStateListOf(true, true, true, true, true) }

    var isRefreshing by remember { mutableStateOf(false) }
    var itemCount by remember { mutableStateOf(15) }
    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            // fetch something
            delay(5000)
            itemCount += 5
            isRefreshing = false
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("游戏库", maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(20.dp)),
                horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
            ) {
                options.forEachIndexed { index, label ->
                    ToggleButton(
                        checked = checked[index],
                        onCheckedChange = { checked[index] = it },
                        shapes =
                            when (index) {
                                0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                            },
                    ) {
                        Icon(
                            if (checked[index]) checkedIcons[index] else unCheckedIcons[index],
                            contentDescription = "Localized description",
                        )
                    }
                }
            }
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                state = state,
                contentAlignment = Alignment.TopStart,
                modifier = Modifier.fillMaxSize(),
                indicator = {
                    PullToRefreshDefaults.LoadingIndicator(
                        state = state,
                        isRefreshing = isRefreshing,
                        modifier = Modifier.align(Alignment.TopCenter),
                    )
                }
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(mockData) { item ->
                        GameLibraryCard()
                    }
                }
            }
        }
    }
}
