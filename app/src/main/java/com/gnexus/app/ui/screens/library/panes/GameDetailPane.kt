package com.gnexus.app.ui.screens.library.panes

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.gnexus.app.ui.screens.library.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@SuppressLint("FrequentlyChangingValue")
@Composable
fun GameDetailPane(
	gameId: String,
	viewModel: LibraryViewModel = hiltViewModel()
) {
	val game by viewModel.getGameDetails(gameId).collectAsState(initial = null)
	val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
	val collapsedFraction = scrollBehavior.state.collapsedFraction

	val expandedHeight = 100.dp
	val collapsedHeight = 64.dp
	Scaffold(
		modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
		topBar = {
			LargeFlexibleTopAppBar(
				scrollBehavior = scrollBehavior,
				expandedHeight = expandedHeight,
				collapsedHeight = collapsedHeight,
				navigationIcon = {
					IconButton(onClick = {}) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, "Navigate back")
					}
				},
				title = {
					Box(
						modifier = Modifier
							.fillMaxWidth()
							.height(expandedHeight)
					) {

						Box(
							modifier = Modifier
								.fillMaxSize()
								.graphicsLayer { alpha = 1f - collapsedFraction }
						) {
							// 占位：视频轮播组件
							AsyncImage(
								model = game?.imageUrl,
								contentDescription = null,
								contentScale = ContentScale.Crop,
								modifier = Modifier.fillMaxSize()
							)
							// 加一层渐变遮罩，确保文字在亮色视频上清晰
							Box(
								modifier = Modifier
									.fillMaxSize()
									.background(
										Brush.verticalGradient(
											0f to Color.Black.copy(0.5f),
											0.3f to Color.Transparent,
											1f to Color.Black.copy(0.7f)
										)
									)
							)
						}
						Box(
							modifier = Modifier
								.fillMaxWidth()
								.height(collapsedHeight) // 固定高度
								.align(Alignment.BottomStart)
						) {
							// --- 内容层：封面、名字、开发商 ---
							FlexibleHeaderContent(
								collapsedFraction = collapsedFraction,
								gameName = game?.localizedName ?: "Loading...",
								developer = game?.service ?: "", // 示例
								rating = "4.8",
								coverUrl = game?.imageUrl ?: ""
							)
						}

					}
				},
//				actions = {
//					IconButton(onClick = {}) {
//						Icon(
//							Icons.Filled.Refresh,
//							contentDescription = "Refresh game info"
//						)
//					}
//				},
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = Color.Transparent, // 展开时透明
					scrolledContainerColor = MaterialTheme.colorScheme.surface // 收缩后显示主题色
				)
			)
		}
	) { paddingValues ->
		LazyColumn(contentPadding = paddingValues) {
			items(50) { Text("Content Item $it", modifier = Modifier.padding(16.dp)) }
		}
	}
}

@Composable
fun FlexibleHeaderContent(
	collapsedFraction: Float,
	gameName: String,
	developer: String,
	rating: String,
	coverUrl: String
) {
	// 这里的 Row/Column 会随着滑动动态调整布局
	val isCollapsed = collapsedFraction > 0.5f

	// 动态计算封面大小
	val imageSize by animateDpAsState(targetValue = if (isCollapsed) 40.dp else 80.dp)
	// 动态计算 Padding (展开时在底部，收缩时在中心)
	val bottomPadding by animateDpAsState(targetValue = if (isCollapsed) 0.dp else 16.dp)


	Row(
		modifier = Modifier
			.fillMaxSize()
			.padding(start = 16.dp, bottom = bottomPadding),
		verticalAlignment = Alignment.CenterVertically
	) {
		// 游戏封面
		AsyncImage(
			model = coverUrl,
			contentDescription = null,
			modifier = Modifier
				.size(imageSize)
				.clip(RoundedCornerShape(8.dp)),
			contentScale = ContentScale.Crop
		)

		Spacer(modifier = Modifier.width(12.dp))

		Column(verticalArrangement = Arrangement.Center) {
			Text(
				text = gameName,
				style = if (isCollapsed) MaterialTheme.typography.titleMedium else MaterialTheme.typography.headlineSmall,
				color = Color.White, // 建议使用白色或适配背景
				fontWeight = FontWeight.Bold,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis
			)

			Text(
				text = developer,
				style = MaterialTheme.typography.labelMedium,
				color = Color.White.copy(alpha = 0.8f)
			)

			// 只有在展开状态下显示评分
			if (!isCollapsed) {
				Text(
					text = "★ $rating",
					style = MaterialTheme.typography.labelSmall,
					color = Color.Yellow,
					modifier = Modifier.padding(top = 4.dp)
				)
			}
		}
	}
}
