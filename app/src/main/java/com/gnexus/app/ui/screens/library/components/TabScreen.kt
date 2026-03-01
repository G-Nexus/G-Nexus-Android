package com.gnexus.app.ui.screens.library.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import com.gnexus.app.data.db.GameEntity
import com.gnexus.app.ui.screens.library.LibraryViewModel
import com.gnexus.app.ui.screens.library.ViewMode
import com.gnexus.app.ui.screens.library.navigation.PlatformDestination
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

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

	// 默认可见。当用户开始向上滚动时，变为false；当用户向下滚动时，变为true。
	var headerVisible by remember { mutableStateOf(true) }

	// --- 监听滚动方向 ---
	LaunchedEffect(listState, gridState) {
		// snapshotFlow将Compose状态转换为Flow
		snapshotFlow { listState.firstVisibleItemScrollOffset }
			.map { it > 0 } // 映射为是否已滚动
			.distinctUntilChanged() // 只有在滚动状态（滚动/未滚动）真正改变时才发出新值
			.collect { isScrolled ->
				// 当列表滚动时，隐藏Header；当列表回到顶部时，显示Header
				// 这是一个简化的逻辑，更复杂的方向判断如下（但通常这个就足够好）
				headerVisible = !isScrolled
			}
	}

	val summaryState = remember { true }

	Column(
		modifier = Modifier.fillMaxSize(),
	) {
		AnimatedVisibility(
			visible = headerVisible,
			enter = expandVertically(animationSpec = tween(300)) + fadeIn(
				animationSpec = tween(
					220,
					90
				)
			),
			exit = shrinkVertically(animationSpec = tween(300)) + fadeOut(animationSpec = tween(90))
		) {
			if (summaryState) {
				PlatformSummaryHeader(pageIndex, onPlatformClick, games.itemCount)
			} else {
				PlatformSummarySkeleton()
			}

		}
		PullToRefreshBox(
			isRefreshing = isRefreshing,
			onRefresh = { viewModel.onRefresh() },
			state = pullToRefreshState,
			contentAlignment = Alignment.TopStart,
			modifier = Modifier.fillMaxSize(),
			indicator = {
				PullToRefreshDefaults.LoadingIndicator(
					state = pullToRefreshState,
					color = MaterialTheme.colorScheme.primary,
					isRefreshing = isRefreshing,
					modifier = Modifier
						.align(Alignment.TopCenter)
						.offset(y = (-82).dp)
				)
			}
		) {
			// --- 核心优化：处理加载状态并显示骨架屏 ---
			val refreshLoadState = games.loadState.refresh

			when (refreshLoadState) {
				is LoadState.Loading -> {
					GameLibrarySkeletonLoader()
				}

//				// 2. 刷新失败
//				is LoadState.Error -> {
//					// 如果Paging没有任何缓存数据，则显示全屏错误
//					if (games.itemCount == 0) {
//						Box(
//							modifier = Modifier.fillMaxSize(),
//							contentAlignment = Alignment.Center
//						) {
//							Text(text = "加载失败，请下拉重试")
//						}
//					}
//				}

				// 3. 刷新成功或从缓存加载完成
				else -> {
					// 检查列表是否为空
					if (games.itemCount == 0) {
						Box(
							modifier = Modifier.fillMaxSize(),
							contentAlignment = Alignment.Center
						) {
							Text(text = "你的游戏库是空的")
						}
					} else {
						when (viewMode) {
							ViewMode.LIST -> LazyColumn(
								state = listState,
								contentPadding = PaddingValues(
									bottom = 12.dp,
									start = 24.dp,
									end = 24.dp,
									top = 0.dp
								),
								horizontalAlignment = Alignment.CenterHorizontally,
							) {
								items(games.itemSnapshotList) { item ->
									GameLibraryCard(
										item,
										onGameClick,
										onTrophyClick,
										onGuideClick
									)
								}
							}

							ViewMode.GRID -> LazyVerticalGrid(
								columns = GridCells.Adaptive(minSize = 120.dp), // 定义网格列数规则
								state = gridState,
								contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
								// ...
							) {
								items(
									count = games.itemCount,
									contentType = games.itemContentType()
								) { item ->
									val item = games[item]
									GameCard(item, onGameClick)
								}
							}

						}
					}
				}
			}
		}
	}
}
