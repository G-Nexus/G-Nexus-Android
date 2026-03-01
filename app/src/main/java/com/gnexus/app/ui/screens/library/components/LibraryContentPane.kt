package com.gnexus.app.ui.screens.library.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.gnexus.app.ui.screens.library.panes.Destination
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryContentPane(
	onTabChanged: (Destination) -> Unit,
	initialPage: Int = 0,
	onGameClick: (Int) -> Unit,
	onTrophyClick: (Int) -> Unit,
	onGuideClick: (Int) -> Unit,
) {
	val destinations = remember { Destination.entries }
	val pagerState = rememberPagerState(
		initialPage = initialPage,
		pageCount = { destinations.size }
	)
	val coroutineScope = rememberCoroutineScope()

	LaunchedEffect(pagerState) {
		snapshotFlow { pagerState.currentPage }.collectLatest { page ->
			onTabChanged(destinations[page])
		}
	}

	Column {
		PrimaryScrollableTabRow(
			selectedTabIndex = pagerState.currentPage,
		) {
			Destination.entries.forEachIndexed { index, destination ->
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
				onGameClick,
				onTrophyClick,
				onGuideClick,
			)
		}
	}


}

/**
 * 一个绘制渐变遮罩的辅助 Composable
 * @param start true表示是左侧的起始遮罩，false表示是右侧的结束遮罩
 */
@Composable
private fun FadingEdge(start: Boolean, width: androidx.compose.ui.unit.Dp = 24.dp) {
	val colors = listOf(
		MaterialTheme.colorScheme.surfaceContainerHigh,
		Color.Transparent
	)
	Box(
		modifier = Modifier
			.fillMaxHeight()
			.width(width)
			.background(
				brush = Brush.horizontalGradient(
					colors = if (start) colors else colors.asReversed()
				)
			)
	)
}