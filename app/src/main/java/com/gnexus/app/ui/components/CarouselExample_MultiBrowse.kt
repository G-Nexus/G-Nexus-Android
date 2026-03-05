package com.gnexus.app.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun CarouselExample_MultiBrowse() {
	data class CarouselItem(
		val id: Int,
		val imgUrl: String,
		val contentDescription: String
	)

	val items = remember {
		listOf(
			CarouselItem(
				0,
				"https://image.api.playstation.com/vulcan/ap/rnd/202512/1205/b2ee4bb116cbc638d085ccf6f8a70926e23a945810ef8696.jpg?w=620&thumb=false",
				"cupcake"
			),
			CarouselItem(
				1,
				"https://image.api.playstation.com/vulcan/ap/rnd/202512/1205/0defaa05f3a37f4340975cb80cbd328462d6f9af93c115b0.jpg?w=620&thumb=false",
				"donut"
			),
			CarouselItem(
				2,
				"https://image.api.playstation.com/vulcan/ap/rnd/202512/1205/42738d3e42f78fab129efa703bd39b56c223d1e9aff488cd.jpg?w=620&thumb=false",
				"eclair"
			),
			CarouselItem(
				3,
				"https://image.api.playstation.com/vulcan/ap/rnd/202512/1205/85f70c7da036a82417a2c4e9bf9dc6876320e22c25def9d9.jpg?w=620&thumb=false",
				"froyo"
			),
			CarouselItem(
				4,
				"https://image.api.playstation.com/vulcan/ap/rnd/202512/1205/f8fc0bb33bb532fd955efc69f5206a487507361406213583.jpg?w=620&thumb=false",
				"gingerbread"
			),
		)
	}

	HorizontalMultiBrowseCarousel(
		state = rememberCarouselState { items.count() },
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight()
			.padding(top = 16.dp, bottom = 16.dp),
		preferredItemWidth = 240.dp,
		itemSpacing = 8.dp,
		contentPadding = PaddingValues(horizontal = 16.dp)
	) { i ->
		val item = items[i]
		AsyncImage(
			modifier = Modifier
				.height(205.dp)
				.maskClip(MaterialTheme.shapes.extraLarge),
			model = item.imgUrl,
			contentDescription = item.contentDescription,
			contentScale = ContentScale.Crop
		)
	}
}
