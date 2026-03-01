package com.gnexus.app.ui.screens.library.panes

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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.gnexus.app.ui.screens.library.LibraryViewModel
import com.gnexus.app.ui.screens.library.ViewMode
import com.gnexus.app.ui.screens.library.components.TabScreen
import com.gnexus.app.ui.screens.library.navigation.PlatformDestination
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(
	ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class,
	ExperimentalFoundationApi::class
)
@Composable
fun GameFeedPane(
	windowSizeClass: WindowSizeClass,
	onGameClick: (Int) -> Unit,
	onTrophyClick: (Int) -> Unit,
	onGuideClick: (Int) -> Unit,
	onPlatformChange: (PlatformDestination) -> Unit,
	onPlatformClick: (PlatformDestination) -> Unit,
	initialPage: Int = 0,
	viewModel: LibraryViewModel = hiltViewModel(),
	viewMode: ViewMode
) {
	val games = viewModel.games.collectAsLazyPagingItems()
	val destinations = remember { PlatformDestination.entries }
	val pagerState =
		rememberPagerState(initialPage = initialPage, pageCount = { destinations.size })
	val coroutineScope = rememberCoroutineScope()
	// 监听 Pager 状态变化，并通过回调通知父组件
	LaunchedEffect(pagerState) {
		snapshotFlow { pagerState.currentPage }.collectLatest { page ->
			val newPlatform = destinations[page]
			onPlatformChange(newPlatform)
			viewModel.onPlatformChanged(newPlatform.route)
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
				selectedTabIndex = pagerState.currentPage,
			) {
				PlatformDestination.entries.forEachIndexed { index, destination ->
					Tab(
						selected = pagerState.currentPage == index,
						onClick = {
							coroutineScope.launch {
								pagerState.animateScrollToPage(index)
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
				modifier = Modifier
					.fillMaxSize()
					.weight(1f)
					.clipToBounds()
			) { pageIndex ->
				TabScreen(
					games,
					pageIndex,
					viewMode,
					onGameClick,
					onTrophyClick,
					onGuideClick,
					onPlatformClick,
				)
			}
		}
	}
}
