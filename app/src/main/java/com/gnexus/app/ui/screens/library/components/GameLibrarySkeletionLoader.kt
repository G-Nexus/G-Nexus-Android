package com.gnexus.app.ui.screens.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//import com.valentinilk.shimmer.shimmer

@Composable
fun GameLibrarySkeletonLoader() {
	// 使用 shimmer 修饰符包裹整个列表
	LazyColumn(
		modifier = Modifier
			.fillMaxSize(),
//			.shimmer(), // 应用闪烁效果
		contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
		userScrollEnabled = false // 加载时禁止滚动
	) {
		items(10) { // 显示10个占位项
			SkeletonGameCard()
		}
	}
}

@Composable
private fun SkeletonGameCard() {
	// 这个布局应该和你真实的 GameLibraryCard 非常相似
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 8.dp)
	) {
		// 游戏封面占位符
		Box(
			modifier = Modifier
				.size(width = 90.dp, height = 120.dp)
				.clip(RoundedCornerShape(8.dp))
				.background(Color.LightGray) // 占位符颜色
		)
		Spacer(modifier = Modifier.width(16.dp))
		Column(
			modifier = Modifier.height(120.dp),
			verticalArrangement = Arrangement.SpaceEvenly
		) {
			// 游戏标题占位符
			Box(
				modifier = Modifier
					.height(20.dp)
					.fillMaxWidth(0.7f)
					.background(Color.LightGray)
			)
			// 副标题占位符
			Box(
				modifier = Modifier
					.height(16.dp)
					.fillMaxWidth(0.5f)
					.background(Color.LightGray)
			)
			// 其他信息占位符
			Box(
				modifier = Modifier
					.height(16.dp)
					.fillMaxWidth(0.6f)
					.background(Color.LightGray)
			)
		}
	}
}