package com.gnexus.app.ui.screens.library.panes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.gnexus.app.ui.screens.library.LibraryPreviewData.mockGame
import com.gnexus.app.ui.screens.library.components.GameLibraryCard
import com.gnexus.app.ui.screens.library.components.PlatformGroupList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GameFeedPane(
    windowSizeClass: WindowSizeClass,
    onGameClick: (Int) -> Unit,
    onTrophyClick: (Int) -> Unit,
    onGuideClick: (Int) -> Unit
) {
    val mockData = Array(10000) { mockGame }
    TextFieldState("")
    val listState = rememberLazyListState()

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
            shape = if (windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium) {
                // 平板 / 大屏 / 折叠展开
                RoundedCornerShape(24.dp)
            } else {
                // 直板手机
                RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            },
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
                        state = listState,
                        contentPadding = PaddingValues(
                            bottom = 12.dp,
                            start = 12.dp,
                            end = 12.dp,
                            top = 50.dp
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(mockData) { item ->
                            GameLibraryCard(
                                onGameClick,
                                onTrophyClick,
                                onGuideClick
                            )
                        }
                    }
                    PlatformGroupList()
                }
            }
        }
    }
}

