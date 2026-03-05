package com.gnexus.app.ui.screens.library.panes

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.gnexus.app.ui.components.CarouselExample_MultiBrowse
import com.gnexus.app.ui.screens.library.LibraryViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@SuppressLint("FrequentlyChangingValue")
@Composable
fun GameDetailPane(
	gameId: String,
	viewModel: LibraryViewModel = hiltViewModel()
) {
	val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
	val collapsedFraction = scrollBehavior.state.collapsedFraction

	val expandedHeight = 150.dp
	val collapsedHeight = 100.dp

	val tabIndex = rememberSaveable { mutableIntStateOf(0) }
	val scrollState = rememberScrollState()

	Scaffold(
		modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
		topBar = {
			LargeFlexibleTopAppBar(
				scrollBehavior = scrollBehavior,
				expandedHeight = expandedHeight,
				collapsedHeight = collapsedHeight,
				navigationIcon = {
					IconButton(onClick = {}) {
						Icon(
							Icons.AutoMirrored.Filled.ArrowBack,
							"Navigate back",
							tint = Color.White
						)
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
							AsyncImage(
								model = "https://image.api.playstation.com/vulcan/ap/rnd/202512/1506/3878ff92261c1fda7ce03772ac149514ce6f6bf5c715e64b.png?w=1024&thumb=false",
								contentDescription = null,
								contentScale = ContentScale.Crop,
								modifier = Modifier.fillMaxSize()
							)
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
							FlexibleHeaderContent(
								collapsedFraction = collapsedFraction,
								gameName = "Resident Evil Requiem",
								developer = "CAPCOM ASIA", // 示例
								rating = "4.8",
								coverUrl = "https://image.api.playstation.com/vulcan/ap/rnd/202512/1205/79661d7a2bdb9784749b4e57e1456ca89f7ac7bed8615aee.png?w=230&thumb=false"
							)
						}

					}
				},
				actions = {
					IconButton(onClick = {}) {
						Icon(Icons.AutoMirrored.Outlined.HelpOutline, contentDescription = null)
					}
				},
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = Color.Transparent, // 展开时透明
					scrolledContainerColor = MaterialTheme.colorScheme.surface // 收缩后显示主题色
				)
			)
		}
	) { contentPadding ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.verticalScroll(scrollState)
				.padding(contentPadding),
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(20.dp),
			) {
				AsyncImage(
					model = "https://image.api.playstation.com/grc/images/ratings/4k/generic/iarc_18.png?w=54&thumb=false",
					contentDescription = null,
					modifier = Modifier
				)
				Column(
					modifier = Modifier
						.padding(start = 10.dp)
						.weight(1f),
				) {
					Text(
						"18+",
						style = MaterialTheme.typography.bodySmall,
						fontSize = TextUnit(10f, TextUnitType.Sp)
					)
					Text(
						"粗暴语言, 极端暴力",
						style = MaterialTheme.typography.bodySmallEmphasized,
						fontSize = TextUnit(10f, TextUnitType.Sp),
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
					HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp))
					Text(
						"游戏内购买",
						style = MaterialTheme.typography.bodySmallEmphasized,
						fontSize = TextUnit(10f, TextUnitType.Sp),
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
			}

			Spacer(modifier = Modifier.height(10.dp))

			Text("进度")
			ElevatedCard(
				modifier = Modifier.padding(20.dp),
				onClick = {}
			) {
				Row(
					horizontalArrangement = Arrangement.SpaceAround,
					verticalAlignment = Alignment.CenterVertically,
					modifier = Modifier
						.fillMaxWidth()
						.padding(10.dp)
						.height(30.dp),
				) {
					Row(
						modifier = Modifier,
						verticalAlignment = Alignment.CenterVertically
					) {
						Icon(Icons.Filled.EmojiEvents, contentDescription = null)
						Spacer(modifier = Modifier.width(4.dp))
						Text("50 / 50", style = MaterialTheme.typography.bodySmall)
					}
					VerticalDivider(
						modifier = Modifier
							.fillMaxHeight()
					)
					Row(
						modifier = Modifier,
						verticalAlignment = Alignment.CenterVertically
					) {
						Icon(Icons.Filled.AccessTime, contentDescription = null)
						Spacer(modifier = Modifier.width(4.dp))
						Text("40小时", style = MaterialTheme.typography.bodySmall)
					}
				}
			}

			SecondaryTabRow(
				selectedTabIndex = tabIndex.intValue,
				modifier = Modifier
					.fillMaxWidth()
					.padding(
						horizontal = 20.dp
					),
				containerColor = MaterialTheme.colorScheme.surface,
			) {
				Tab(
					selected = tabIndex.intValue == 0,
					onClick = { tabIndex.intValue = 0 },
					text = {
						Text(
							"游戏详细",
							style = MaterialTheme.typography.bodySmallEmphasized
						)
					}
				)

				Tab(
					selected = tabIndex.intValue == 1,
					onClick = { tabIndex.intValue = 1 },
					text = { Text("功能", style = MaterialTheme.typography.bodySmallEmphasized) }
				)


				Tab(
					selected = tabIndex.intValue == 2,
					onClick = { tabIndex.intValue = 2 },
					text = { Text("更多", style = MaterialTheme.typography.bodySmallEmphasized) }
				)
			}
			when (tabIndex.intValue) {
				0 ->
					Column {
						CarouselExample_MultiBrowse()
						Text(
							"描述:",
							style = MaterialTheme.typography.bodySmallEmphasized,
							modifier = Modifier.padding(start = 20.dp)
						)
						Text(
							"《Resident Evil Requiem》是開創生存恐怖新紀元的系列最新作。與FBI分析員葛蕾斯一同體驗令人渾身顫抖的恐懼，並與資深特工里昂一同感受戰勝死亡的爽快感。雙角色的遊戲體驗，以及圍繞二人縱橫交錯的劇情，絕對能撼動玩家的精神深處。\n" +
									"\n" +
									"*另有包含本商品的套裝包。請注意避免重複購買。\n" +
									"\n" +
									"©CAPCOM\n" +
									"RESIDENT EVIL is a trademark and/or registered trademark of CAPCOM CO., LTD. and/or its subsidiaries in the U.S. and/or other countries.",
							style = MaterialTheme.typography.bodySmall,
							modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
						)
						Text("开发商:", style = MaterialTheme.typography.bodySmallEmphasized)
						Text("CAPCOM CO., LTD.", style = MaterialTheme.typography.bodySmall)
						Text("发行商:", style = MaterialTheme.typography.bodySmallEmphasized)
						Text("CAPCOM CO., LTD.", style = MaterialTheme.typography.bodySmall)
						Text("发行日期:", style = MaterialTheme.typography.bodySmallEmphasized)
						Text("2026/2/27", style = MaterialTheme.typography.bodySmall)
					}

				1 -> Text("功能")
				2 -> Text("更多")
			}
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
			.padding(bottom = bottomPadding)
			.height(imageSize),
	) {
		// 游戏封面
		Box(
			modifier = Modifier
				.size(imageSize)
				.graphicsLayer {
					// 展开时增加投影
					shadowElevation = 20f * (1f - collapsedFraction)
					shape = RoundedCornerShape(8.dp)
					clip = true
				}
		) {
			AsyncImage(
				model = coverUrl,
				contentDescription = null,
				modifier = Modifier
					.size(imageSize)
					.clip(RoundedCornerShape(8.dp)),
				contentScale = ContentScale.Crop
			)
			Box(
				modifier = Modifier
					.fillMaxSize()
					.background(
						Brush.verticalGradient(
							colors = listOf(
								Color.Transparent,
								Color.Black.copy(alpha = 0.4f * (1f - collapsedFraction))
							),
							startY = 0.6f // 从图片的 60% 位置开始渐变
						)
					)
			)
		}

		Spacer(modifier = Modifier.width(12.dp))

		Column(verticalArrangement = Arrangement.Center) {
			Text(
				text = gameName,
				style = if (isCollapsed) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodySmall,
				color = Color.White, // 建议使用白色或适配背景
				fontWeight = FontWeight.Bold,
			)

			Text(
				text = developer,
				style = MaterialTheme.typography.bodySmall,
				color = Color.White.copy(alpha = 0.8f)
			)

			// 只有在展开状态下显示评分
			if (!isCollapsed) {
				Text(
					text = "★ $rating",
					style = MaterialTheme.typography.bodySmall,
					color = Color.Yellow,
					modifier = Modifier.padding(top = 4.dp)
				)
			}
		}
	}
}
