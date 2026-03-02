package com.gnexus.app.ui.screens.library.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.gnexus.app.data.db.GameEntity
import com.gnexus.app.ui.screens.library.PlatformSummaryUiState
import com.gnexus.app.ui.screens.library.ViewMode
import com.gnexus.app.ui.screens.library.navigation.PlatformDestination
import kotlinx.coroutines.launch

@OptIn(
	ExperimentalMaterial3Api::class,
	ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun TabScreen(
	games: LazyPagingItems<GameEntity>,
	pageIndex: Int,
	viewMode: ViewMode,
	summaryState: PlatformSummaryUiState,
	isRefreshing: Boolean,
	onGameClick: (String) -> Unit,
	onTrophyClick: (Int) -> Unit,
	onRefresh: () -> Unit,
	onGuideClick: (Int) -> Unit,
	onPlatformClick: (PlatformDestination) -> Unit,
) {
	val listState = rememberLazyListState()
	val gridState = rememberLazyGridState()

	val coroutineScope = rememberCoroutineScope()
	val pullToRefreshState = rememberPullToRefreshState()
	val density = LocalDensity.current

	// 定义隐藏 Header 的滚动距离阈值
	val headerHideThresholdPx = with(density) { 40.dp.toPx() }
	val headerVisible by remember(viewMode) {
		derivedStateOf {
			val (index, offset) = when (viewMode) {
				ViewMode.LIST -> listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset
				ViewMode.GRID -> gridState.firstVisibleItemIndex to gridState.firstVisibleItemScrollOffset
			}
			// 判断逻辑：如果在第一个 Item 且滚动距离小于阈值，则保持显示
			index == 0 && offset < headerHideThresholdPx
		}
	}

	val fabVisible by remember(viewMode) {
		derivedStateOf {
			val index = when (viewMode) {
				ViewMode.LIST -> listState.firstVisibleItemIndex
				ViewMode.GRID -> gridState.firstVisibleItemIndex
			}
			index > 2
		}
	}

	// 当viewMode切换时，重置列表滚动到顶部
	LaunchedEffect(viewMode) {
		listState.scrollToItem(0)
		gridState.scrollToItem(0)
	}
	val headerHeight = 50.dp
	Box(
		modifier = Modifier.fillMaxSize(),
	) {
		PullToRefreshBox(
			isRefreshing = isRefreshing,
			onRefresh = onRefresh,
			state = pullToRefreshState,
			contentAlignment = Alignment.TopStart,
			modifier = Modifier.fillMaxSize(),
			indicator = {
				PullToRefreshDefaults.Indicator(
					state = pullToRefreshState,
					isRefreshing = isRefreshing,
					modifier = Modifier.align(Alignment.TopCenter)
				)
			}
		) {
			val refreshLoadState = games.loadState.refresh

			if (refreshLoadState is LoadState.Loading && games.itemCount == 0) {
				Column {
					Spacer(modifier = Modifier.height(headerHeight + 16.dp))
					GameLibrarySkeletonLoader()
				}
			} else if (refreshLoadState is LoadState.Error && games.itemCount == 0) {
				Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					Text(text = "加载失败，请下拉重试")
				}
			} else if (games.loadState.append.endOfPaginationReached && games.itemCount == 0) {
				Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					Text(text = "你的游戏库是空的")
				}
			} else {
				when (viewMode) {
					ViewMode.LIST -> LazyColumn(
						state = listState,
						contentPadding = PaddingValues(24.dp, 24.dp, 24.dp, 80.dp),
						horizontalAlignment = Alignment.CenterHorizontally,
					) {
						item {
							Spacer(modifier = Modifier.height(headerHeight))
						}
						items(
							count = games.itemCount,
							key = games.itemKey { it.titleId }
						) { index ->
							val item = games[index]
							if (item != null) {
								GameLibraryCard(item, onGameClick, onTrophyClick, onGuideClick)
							}
						}
					}

					ViewMode.GRID -> LazyVerticalGrid(
						columns = GridCells.Adaptive(minSize = 100.dp),
						state = gridState,
						contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 80.dp),
					) {
						item(span = { GridItemSpan(maxLineSpan) }) {
							Spacer(modifier = Modifier.height(headerHeight + 8.dp))
						}
						items(
							count = games.itemCount,
							key = games.itemKey { it.titleId },
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
		}

		AnimatedVisibility(
			visible = headerVisible,
			enter = expandVertically(animationSpec = tween(300)) + fadeIn(
				animationSpec = tween(
					200,
					100
				)
			),
			exit = shrinkVertically(animationSpec = tween(300)) + fadeOut(animationSpec = tween(100)),
			modifier = Modifier.align(Alignment.TopCenter)
		) {
			when (summaryState) {
				is PlatformSummaryUiState.Loading -> {
					Surface(color = MaterialTheme.colorScheme.background) {
						PlatformSummarySkeleton()
					}
				}

				is PlatformSummaryUiState.Success -> PlatformSummaryHeader(
					summaryState.summary,
					games.itemCount,
					onPlatformClick,
					PlatformDestination.entries[pageIndex]
				)

				is PlatformSummaryUiState.Error -> {
					Surface(color = MaterialTheme.colorScheme.background) {
						PlatformSummarySkeleton()
					}
				}
			}
		}

		AnimatedVisibility(
			visible = fabVisible,
			modifier = Modifier
				.align(Alignment.BottomEnd)
				.padding(16.dp),
			enter = slideInVertically(initialOffsetY = { it * 2 }),
			exit = slideOutVertically(targetOffsetY = { it * 2 })
		) {
			FloatingActionButton(
				onClick = {
					coroutineScope.launch {
						// 点击FAB时，平滑地滚动回顶部
						when (viewMode) {
							ViewMode.LIST -> listState.animateScrollToItem(0)
							ViewMode.GRID -> gridState.animateScrollToItem(0)
						}
					}
				},
				containerColor = MaterialTheme.colorScheme.primaryContainer,
				contentColor = MaterialTheme.colorScheme.onPrimaryContainer
			) {
				Icon(Icons.Default.ArrowUpward, contentDescription = "Scroll to top")
			}
		}
	}
}