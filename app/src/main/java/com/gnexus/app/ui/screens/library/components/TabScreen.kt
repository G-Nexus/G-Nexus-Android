package com.gnexus.app.ui.screens.library.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.gnexus.app.R
import com.gnexus.app.ui.components.Avatar
import com.gnexus.app.ui.screens.library.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TabScreen(
	onGameClick: (Int) -> Unit,
	onTrophyClick: (Int) -> Unit,
	onGuideClick: (Int) -> Unit,
	viewModel: LibraryViewModel = hiltViewModel()
) {
	val games = viewModel.games.collectAsLazyPagingItems()
	TextFieldState("")
	val listState = rememberLazyListState()

	rememberCoroutineScope()

	val pullToRefreshState = rememberPullToRefreshState()

	// --- 状态提升：从ViewModel获取刷新状态和触发刷新 ---
	val isRefreshing by viewModel.isRefreshing.collectAsState()
	rememberPullToRefreshState()

	Column(
		modifier = Modifier.fillMaxSize(),
	) {
		Row(
			modifier = Modifier
				.padding(horizontal = 24.dp, vertical = 8.dp)
				.height(50.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Avatar(
				"https://psn-rsc.prod.dl.playstation.net/psn-rsc/avatar/HP9000/PPSA01971_00-DEATHSDCAVATAR11_4E1229519D7858373038_xl.png",
				Modifier.size(42.dp)
			)
			Column(
				modifier = Modifier.padding(start = 8.dp)
			) {
				Row(
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = "FujiiCat",
						fontSize = MaterialTheme.typography.bodySmall.fontSize,
						modifier = Modifier
							.padding(end = 8.dp)
					)
					Image(
						painter = painterResource(R.drawable.psn),
						contentDescription = "psn_plus",
						modifier = Modifier.size(15.dp),
					)
				}
				Text("jonas-lang-garfi", fontSize = MaterialTheme.typography.bodySmall.fontSize)
			}
			Spacer(Modifier.weight(1f))
			Column(
				horizontalAlignment = Alignment.End
			) {
				Text("200 款游戏", fontSize = MaterialTheme.typography.bodySmall.fontSize)
				Text("2000 个奖杯", fontSize = MaterialTheme.typography.bodySmall.fontSize)
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
//						.padding(top = 80.dp)
				)
			}
		) {
			// --- 核心优化：处理加载状态并显示骨架屏 ---
			val refreshLoadState = games.loadState.refresh

			when (// 1. 正在刷新，或初始加载 (即使有缓存也显示骨架屏)
				refreshLoadState) {
				is LoadState.Loading -> {
					GameLibrarySkeletonLoader()
				}

				// 2. 刷新失败
				is LoadState.Error -> {
					// 如果Paging没有任何缓存数据，则显示全屏错误
					if (games.itemCount == 0) {
						Box(
							modifier = Modifier.fillMaxSize(),
							contentAlignment = Alignment.Center
						) {
							Text(text = "加载失败，请下拉重试")
						}
					}
					// 如果有缓存数据，Paging会自动显示旧数据，这里我们什么都不做，
					// 让用户看到旧列表。如果你不希望这样，可以在这里也返回骨架屏。
					else {
						LazyColumn(
							state = listState,
							contentPadding = PaddingValues(
								bottom = 12.dp,
								start = 24.dp,
								end = 24.dp,
								top = 0.dp
							),
							horizontalAlignment = Alignment.CenterHorizontally
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
					}
				}

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
						LazyColumn(
							state = listState,
							contentPadding = PaddingValues(
								bottom = 12.dp,
								start = 24.dp,
								end = 24.dp,
								top = 0.dp
							),
							horizontalAlignment = Alignment.CenterHorizontally
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
					}
				}
			}
		}
	}
}
