package com.gnexus.app.ui.screens.library.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.gnexus.app.data.db.GameEntity
import com.gnexus.app.ui.screens.library.LibraryViewModel
import com.gnexus.app.ui.screens.library.ViewMode
import com.gnexus.app.ui.screens.library.navigation.PlatformDestination

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TabScreen(
	games: LazyPagingItems<GameEntity>,
	pageIndex: Int,
	viewMode: ViewMode,
	onGameClick: (Int) -> Unit,
	onTrophyClick: (Int) -> Unit,
	onGuideClick: (Int) -> Unit,
	onPlatformClick: (PlatformDestination) -> Unit,
	viewModel: LibraryViewModel = hiltViewModel(),
) {
	val listState = rememberLazyListState()
	val gridState = rememberLazyGridState()

	val pullToRefreshState = rememberPullToRefreshState()
	val isRefreshing by viewModel.isRefreshing.collectAsState()

	// 当页面切换时，通知ViewModel加载新数据
	LaunchedEffect(pageIndex) {
		viewModel.onPlatformChanged(PlatformDestination.entries[pageIndex].route)
	}

	val summaryState = remember { true }
	74.dp

	Box(
		modifier = Modifier.fillMaxSize(),
	) {
		PullToRefreshBox(
			isRefreshing = isRefreshing,
			onRefresh = { viewModel.onRefresh() },
			state = pullToRefreshState,
			contentAlignment = Alignment.TopStart,
			modifier = Modifier
				.fillMaxSize(),
			indicator = {
				PullToRefreshDefaults.LoadingIndicator(
					state = pullToRefreshState,
					color = MaterialTheme.colorScheme.primary,
					isRefreshing = isRefreshing,
					modifier = Modifier.align(Alignment.TopCenter)
				)
			}
		) {
			// --- 核心优化：处理加载状态并显示骨架屏 ---
			val refreshLoadState = games.loadState.refresh

			if (refreshLoadState is LoadState.Error && games.itemCount == 0) {
				GameLibrarySkeletonLoader()
			} else if (refreshLoadState is LoadState.Error && games.itemCount == 0) {
				Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					Text(text = "加载失败，请下拉重试")
				}
			} else if (games.loadState.append.endOfPaginationReached && games.itemCount == 0) {
				// 只有在Paging完全加载完毕后，才能确定列表是真的为空
				Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					Text(text = "你的游戏库是空的")
				}
			} else {
				// 条件渲染列表或网格
				when (viewMode) {
					ViewMode.LIST -> LazyColumn(
						state = listState,
						contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 72.dp, bottom = 24.dp),
						horizontalAlignment = Alignment.CenterHorizontally,
					) {
						// 7. 使用现代、安全的Paging API
						items(
							count = games.itemCount,
							key = games.itemKey { it.hashCode() } // 推荐使用唯一ID, e.g., it.id
						) { index ->
							val item = games[index]
							if (item != null) {
								GameLibraryCard(
									item,
									onGameClick,
									onTrophyClick,
									onGuideClick
								)
							}
						}
					}

					ViewMode.GRID -> LazyVerticalGrid(
						columns = GridCells.Adaptive(minSize = 120.dp),
						state = gridState,
						contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 72.dp, bottom = 24.dp),
					) {
						items(
							count = games.itemCount,
							key = games.itemKey { it.hashCode() },
							contentType = games.itemContentType()
						) { index ->
							val item = games[index]
							if (item != null) {
								GameCard(item, onGameClick)
							}
						}
					}
				}
			}

			if (summaryState) {
				Box(modifier = Modifier.align(Alignment.TopCenter)) {
					PlatformSummaryHeader(pageIndex, onPlatformClick, games.itemCount)
				}
			}
		}

	}
}
