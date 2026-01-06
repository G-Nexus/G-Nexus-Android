package com.gnexus.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gnexus.app.navigation.GNAppScreen
import com.gnexus.app.navigation.mainNavigationItems

@Composable
fun GNexusAdaptiveApp(
    windowSizeClass: WindowSizeClass
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // 1. 自适应导航套件
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            mainNavigationItems.forEach { screen ->
                item(
                    icon = {
                        Icon(
                            screen.icon,
                            contentDescription = stringResource(screen.labelRes)
                        )
                    },
                    label = { Text(text = stringResource(screen.labelRes)) }, // 显示对应语言的文字
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) {
        // 2. 页面容器
        NavHost(
            navController = navController,
            startDestination = GNAppScreen.Library.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(GNAppScreen.Profile.route) {
                // 具体的 ProfileScreen()
            }
            composable(GNAppScreen.Forum.route) {
//                ForumScreen() // 之前实现的瀑布流页面
            }
            composable(GNAppScreen.Library.route) {
//                MyGameLibraryScreen() // 之前实现的分栏页面
            }
            composable(GNAppScreen.Store.route) {
                // 具体的 StoreScreen()
            }
        }
    }
}