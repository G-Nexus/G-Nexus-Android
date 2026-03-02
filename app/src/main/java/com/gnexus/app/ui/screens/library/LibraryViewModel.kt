package com.gnexus.app.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gnexus.app.data.db.GameEntity
import com.gnexus.app.data.repository.GameRepository
import com.gnexus.app.ui.screens.library.model.PlatformSummary
import com.gnexus.app.ui.screens.library.navigation.PlatformDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

// --- UI State Definitions ---

enum class SortOrder { RECENT, A_TO_Z, Z_TO_A }
enum class ViewMode { LIST, GRID }

sealed interface PlatformSummaryUiState {
	data object Loading : PlatformSummaryUiState
	data class Success(val summary: PlatformSummary) : PlatformSummaryUiState
	data class Error(val message: String?) : PlatformSummaryUiState
}

data class LibraryUiState(
	val currentPlatform: PlatformDestination = PlatformDestination.PSN,
	val sortOrder: SortOrder = SortOrder.RECENT,
	val viewMode: ViewMode = ViewMode.LIST,
	val isRefreshing: Boolean = false,
	val platformSummaryState: PlatformSummaryUiState = PlatformSummaryUiState.Loading
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LibraryViewModel @Inject constructor(
	private val repository: GameRepository,
) : ViewModel() {
	private val _uiState = MutableStateFlow(LibraryUiState())
	val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

	private val gamesCache =
		ConcurrentHashMap<Pair<PlatformDestination, SortOrder>, Flow<PagingData<GameEntity>>>()

	val games: Flow<PagingData<GameEntity>> = _uiState
		.map { it.currentPlatform to it.sortOrder }
		.distinctUntilChanged()
		.flatMapLatest { (platform, sortOrder) ->
			// 3. 实现“按需创建，按需缓存”的逻辑
			val cacheKey = Pair(platform, sortOrder)

			// getOrPut 是一个原子操作，它会检查key是否存在
			// 如果存在，直接返回值
			// 如果不存在，执行lambda创建值，存入map，然后返回值
			gamesCache.getOrPut(cacheKey) {
				repository.getGames(platform.route, sortOrder)
					.cachedIn(viewModelScope) // 只有在新创时才调用cachedIn
			}
		}

	// 预缓存所有平台的游戏数据
	private val allGamesCache: Map<PlatformDestination, Flow<PagingData<GameEntity>>> =
		PlatformDestination.entries.associateWith { platform ->
			// 为每个平台创建一个数据流，并让它在ViewModel的生命周期内缓存
			repository.getGames(platform.route, SortOrder.RECENT) // 初始都按RECENT排序
				.cachedIn(viewModelScope)
		}


	init {
		// 首次进入时，加载用户以添加的默认平台的概要信息
		fetchPlatformSummary(PlatformDestination.PSN.route)
	}

	// --- Event Handlers ---

	fun onPlatformChanged(newPlatform: PlatformDestination) {
		_uiState.update { it.copy(currentPlatform = newPlatform) }
		fetchPlatformSummary(newPlatform.route)
	}

	fun onSortOrderChanged(sortOrder: SortOrder) {
		_uiState.update { it.copy(sortOrder = sortOrder) }
		// 这里需要触发数据流的重新获取，一种方式是让repository的getGames也响应sortOrder
		// 这需要更复杂的 Flow combine 逻辑，我们先简化处理
	}

	fun onViewModeChanged() {
		val newMode = if (_uiState.value.viewMode == ViewMode.LIST) ViewMode.GRID else ViewMode.LIST
		_uiState.update { it.copy(viewMode = newMode) }
	}

	fun onRefresh() {
		viewModelScope.launch {
			// 1. 开始刷新状态
			_uiState.update { it.copy(isRefreshing = true) }

			try {
				// 2. 触发 Paging 3 的数据刷新（可选，如果你的 Repository 支持）
				// 如果你使用了 RemoteMediator，通常不需要在这里手动操作，
				// 只需要在 UI 层调用 pagingItems.refresh()。
				// 但如果你想在 ViewModel 层清理缓存，可以清空 gamesCache。
//				gamesCache.clear()

				// 3. 使用 delay 代替 sleep，模拟网络请求耗时
				delay(2000)


				// 4. 重新获取平台概要信息
				fetchPlatformSummary(_uiState.value.currentPlatform.route)

			} catch (e: Exception) {
				// 处理异常
			} finally {
				// 5. 无论成功失败，最后关闭刷新动画
				_uiState.update { it.copy(isRefreshing = false) }
			}
		}
	}

	private fun fetchPlatformSummary(platformRoute: String) {
		viewModelScope.launch {
			_uiState.update { it.copy(platformSummaryState = PlatformSummaryUiState.Loading) }
			try {
				// repository.forceRefresh() 方法
				val summary = PlatformSummary(
					"PSN",
					"FujiCat",
					"https://psn-rsc.prod.dl.playstation.net/psn-rsc/avatar/HP9000/PPSA01971_00-DEATHSDCAVATAR11_4E1229519D7858373038_xl.png",
					300,
					200
				)
				// val summary = repository.getPlatformSummary(platformRoute)
				_uiState.update {
					it.copy(
						platformSummaryState = PlatformSummaryUiState.Success(
							summary
						)
					)
				}
			} catch (e: Exception) {
				_uiState.update { it.copy(platformSummaryState = PlatformSummaryUiState.Error(e.message)) }
			}
		}
	}

	fun getGameDetails(gameId: String): Flow<GameEntity?> {
		return repository.getGameByTitleId(gameId)
	}
}