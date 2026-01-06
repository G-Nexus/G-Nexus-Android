package com.gnexus.app.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.ui.graphics.vector.ImageVector
import com.gnexus.app.R

sealed class GNAppScreen(
    val route: String,
    @StringRes val labelRes: Int,
    val icon: ImageVector
) {
    object Profile : GNAppScreen("profile", R.string.nav_profile, Icons.Default.Person)
    object Forum : GNAppScreen("forum", R.string.nav_forum, Icons.Default.Forum)
    object Library : GNAppScreen("library", R.string.nav_library, Icons.Default.VideogameAsset)
    object Store : GNAppScreen("store", R.string.nav_store, Icons.Default.ShoppingCart)

    // 如果后续有不显示在导航栏的子页面
//    object GameDetail : GameAppScreen("game_detail/{gameId}", "详情", Icons.Default.Info)
}

// 供 NavigationSuiteScaffold 使用的列表
val mainNavigationItems = listOf(
    GNAppScreen.Library,
    GNAppScreen.Store,
    GNAppScreen.Forum,
    GNAppScreen.Profile,
)