package com.gnexus.app.ui.screens.library.panes

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.gnexus.app.data.db.GameEntity
import com.gnexus.app.ui.screens.library.LibraryUiState
import com.gnexus.app.ui.screens.library.components.TabScreen
import com.gnexus.app.ui.screens.library.navigation.PlatformDestination
import kotlinx.coroutines.launch

@OptIn(
	ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
	ExperimentalFoundationApi::class
)
@Composable
fun GameFeedPane(
	uiState: LibraryUiState,
	games: LazyPagingItems<GameEntity>,
	windowSizeClass: WindowSizeClass,
	onGameClick: (String) -> Unit,
	onTrophyClick: (Int) -> Unit,
	onGuideClick: (Int) -> Unit,
	onPlatformChange: (PlatformDestination) -> Unit,
	onPlatformClick: (PlatformDestination) -> Unit,
	onRefresh: () -> Unit,
	initialPage: Int = 0,
) {
	val destinations = remember { PlatformDestination.entries }
	val pagerState =
		rememberPagerState(initialPage = initialPage, pageCount = { destinations.size })
	val coroutineScope = rememberCoroutineScope()

	// 监听 Pager 页面结算，同步给 ViewModel
	LaunchedEffect(pagerState.settledPage) {
		onPlatformChange(destinations[pagerState.settledPage])
	}

	// 监听 ViewModel 状态，反向同步给 Pager
	LaunchedEffect(uiState.currentPlatform) {
		val targetIndex = uiState.currentPlatform.ordinal
		// 只有在 Pager 处于静止状态时才从外部强制同步，防止拉扯
		if (!pagerState.isScrollInProgress && pagerState.currentPage != targetIndex) {
			pagerState.animateScrollToPage(targetIndex)
		}
	}
	Surface(
		modifier = Modifier
			.fillMaxSize(),
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
		Column {
			PrimaryScrollableTabRow(
				selectedTabIndex = if (pagerState.isScrollInProgress) {
					pagerState.targetPage
				} else {
					pagerState.currentPage
				},
			) {
				destinations.forEachIndexed { index, destination ->
					Tab(
						selected = pagerState.currentPage == index,
						onClick = {
							if (pagerState.currentPage != index) {
								// 只驱动 PagerState，让 PagerState 的 settledPage 自动去同步 ViewModel
								coroutineScope.launch {
									pagerState.animateScrollToPage(
										page = index,
										animationSpec = tween(durationMillis = 400)
									)
								}
							}
						},
						icon = {
							Image(
								painter = painterResource(destination.icon),
								contentDescription = destination.contentDescription,
								modifier = Modifier.size(24.dp),
								colorFilter = ColorFilter.tint(
									MaterialTheme.colorScheme.onSurface
								),
							)
						}
					)
				}
			}
			HorizontalPager(
				state = pagerState,
				beyondViewportPageCount = 1,
				modifier = Modifier
					.fillMaxSize()
					.weight(1f)
					.clipToBounds()
			) { pageIndex ->
				TabScreen(
					games = games,
					pageIndex = pageIndex,
					viewMode = uiState.viewMode,
					summaryState = uiState.platformSummaryState,
					isRefreshing = uiState.isRefreshing,
					onRefresh = onRefresh,
					onGameClick = onGameClick,
					onTrophyClick = onTrophyClick,
					onGuideClick = onGuideClick,
					onPlatformClick = onPlatformClick
				)
			}
		}
	}
}
