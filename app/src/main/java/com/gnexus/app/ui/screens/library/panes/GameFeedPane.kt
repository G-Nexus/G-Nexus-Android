package com.gnexus.app.ui.screens.library.panes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.gnexus.app.R
import com.gnexus.app.ui.screens.library.LibraryPreviewData.mockGame
import com.gnexus.app.ui.screens.library.components.GameLibraryCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GameFeedPane() {
    val mockData = Array(20) { mockGame }
    TextFieldState("")

    val options = listOf("PlayStation", "Xbox", "Steam", "NintendoSwitch", "EpicGames")
    val icons: List<Int> = listOf(
        R.drawable.playstation,
        R.drawable.xbox,
        R.drawable.steam,
        R.drawable.nintendo_switch,
        R.drawable.epicgames
    )
    val checked = remember { mutableStateListOf(true, true, true, true, true) }

    var isRefreshing by remember { mutableStateOf(false) }
    val state = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            // fetch something
            delay(5000)
            isRefreshing = false
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "游戏库",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.5.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {

            Column(
                modifier = Modifier

            ) {

                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = onRefresh,
                    state = state,
                    contentAlignment = Alignment.TopStart,
                    modifier = Modifier.fillMaxSize(),
                    indicator = {
                        PullToRefreshDefaults.LoadingIndicator(
                            state = state,
                            color = MaterialTheme.colorScheme.primary,
                            isRefreshing = isRefreshing,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .zIndex(1f)
                                .padding(top = 60.dp)
                        )
                    }
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            bottom = 12.dp,
                            start = 12.dp,
                            end = 12.dp,
                            top = 40.dp
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(mockData) { item ->
                            GameLibraryCard()
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(topStart = 28.dp),
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
            }
        }
    }
}
