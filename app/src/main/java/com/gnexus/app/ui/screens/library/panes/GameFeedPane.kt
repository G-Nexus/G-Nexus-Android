package com.gnexus.app.ui.screens.library.panes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlaylistAddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.gnexus.app.R
import com.gnexus.app.ui.screens.library.LibraryViewModel
import com.gnexus.app.ui.screens.library.components.GameLibraryCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SongsScreen(
	onGameClick: (Int) -> Unit,
	onTrophyClick: (Int) -> Unit,
	onGuideClick: (Int) -> Unit,
	viewModel: LibraryViewModel = hiltViewModel()
) {
	val games = viewModel.games.collectAsLazyPagingItems()
	TextFieldState("")
	val listState = rememberLazyListState()

	var isRefreshing by remember { mutableStateOf(false) }
	val state = rememberPullToRefreshState()
	val coroutineScope = rememberCoroutineScope()
	val onRefresh: () -> Unit = {
		isRefreshing = true
		coroutineScope.launch {
			// fetch something
			delay(5000)
			isRefreshing = false
		}
	}


	Column(
		modifier = Modifier.fillMaxSize(),
	) {
		PullToRefreshBox(
			isRefreshing = isRefreshing,
			onRefresh = onRefresh,
			state = state,
			contentAlignment = Alignment.TopStart,
			modifier = Modifier.fillMaxSize(),
			indicator = {
				PullToRefreshDefaults.LoadingIndicator(
					state = state,
					color = MaterialTheme.colorScheme.primary,
					isRefreshing = isRefreshing,
					modifier = Modifier
						.align(Alignment.TopCenter)
						.zIndex(1f)
						.padding(top = 80.dp)
				)
			}
		) {
			LazyColumn(
				state = listState,
				contentPadding = PaddingValues(
					bottom = 12.dp,
					start = 12.dp,
					end = 12.dp,
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

@Composable
fun AlbumScreen(modifier: Modifier = Modifier) {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		Text("Album Screen")
	}
}

@Composable
fun PlaylistScreen(modifier: Modifier = Modifier) {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		Text("Playlist Screen")
	}
}

enum class Destination(
	val route: String,
	val label: String,
	val icon: ImageVector,
	val contentDescription: String
) {
	SONGS("songs", "Songs", Icons.Default.MusicNote, "Songs"),
	ALBUM("album", "Album", Icons.Default.Album, "Album"),
	PLAYLISTS("playlist", "Playlist", Icons.Default.PlaylistAddCircle, "Playlist")
}

@Composable
fun AppNavHost(
	navController: NavHostController,
	startDestination: Destination,
	modifier: Modifier = Modifier,
	onGameClick: (Int) -> Unit,
	onTrophyClick: (Int) -> Unit,
	onGuideClick: (Int) -> Unit,
) {
	NavHost(
		navController,
		startDestination = startDestination.route
	) {
		Destination.entries.forEach { destination ->
			composable(destination.route) {
				when (destination) {
					Destination.SONGS -> SongsScreen(
						onGameClick,
						onTrophyClick,
						onGuideClick
					)

					Destination.ALBUM -> AlbumScreen()
					Destination.PLAYLISTS -> PlaylistScreen()
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun GameFeedPane(
	windowSizeClass: WindowSizeClass,
	onGameClick: (Int) -> Unit,
	onTrophyClick: (Int) -> Unit,
	onGuideClick: (Int) -> Unit,
	viewModel: LibraryViewModel = hiltViewModel()
) {

	val navController = rememberNavController()
	val startDestination = Destination.SONGS
	var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }


	val icons: List<Int> = listOf(
		R.drawable.playstation,
		R.drawable.xbox,
		R.drawable.steam,
		R.drawable.nintendo_switch,
		R.drawable.epicgames
	)

	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(
						text = "游戏库",
						style = MaterialTheme.typography.headlineSmall.copy(
							fontWeight = FontWeight.SemiBold,
							letterSpacing = 0.5.sp
						),
						maxLines = 1,
						overflow = TextOverflow.Ellipsis
					)
				},
			)
		}
	) { innerPadding ->
		Surface(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding),
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
				PrimaryTabRow(
					selectedTabIndex = selectedDestination,
				) {
					Destination.entries.forEachIndexed { index, destination ->
						Tab(
							selected = selectedDestination == index,
							onClick = {
								navController.navigate(route = destination.route)
								selectedDestination = index
							},
							icon = {
								Image(
									painter = painterResource(icons[index]),
									contentDescription = null,
									modifier = Modifier.size(24.dp),
									colorFilter = ColorFilter.tint(
										MaterialTheme.colorScheme.onBackground
									),
								)
							}
						)
					}
				}
				AppNavHost(
					navController, startDestination,
					onGameClick = onGameClick,
					onTrophyClick = onTrophyClick,
					onGuideClick = onGuideClick
				)
			}
		}
	}
}

