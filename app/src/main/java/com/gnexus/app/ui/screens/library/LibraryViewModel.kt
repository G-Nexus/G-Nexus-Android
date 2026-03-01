package com.gnexus.app.ui.screens.library

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gnexus.app.data.db.GameEntity
import com.gnexus.app.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortOrder {
	RECENT, // 最近游玩
	A_TO_Z,   // A-Z排序
	Z_TO_A    // Z-A 排序
}

@HiltViewModel
class LibraryViewModel @Inject constructor(
	private val repository: GameRepository,
) : ViewModel() {
	private val _platform = MutableStateFlow("psn") // 默认加载 PSN 数据

	private val _sortOrder = MutableStateFlow(SortOrder.RECENT) // 默认按最近游玩排序

	fun onSortOrderChanged(sortOrderIndex: Int) {
		_sortOrder.value = when (sortOrderIndex) {
			0 -> SortOrder.RECENT
			1 -> SortOrder.A_TO_Z
			2 -> SortOrder.Z_TO_A
			else -> SortOrder.RECENT
		}
	}

	/**
	 * 当用户切换Tab时，由UI调用此方法来更新平台。
	 */
	fun onPlatformChanged(newPlatform: String) {
		_platform.value = newPlatform
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	val games: Flow<PagingData<GameEntity>> =
		combine(_platform, _sortOrder) { platform, sortOrder ->
			// 创建一个 Pair 或自定义的 data class 来持有组合后的状态
			Pair(platform, sortOrder)
		}.flatMapLatest { (platform, sortOrder) ->
			// 假设 repository.getGames 现在可以接收 platform 和 sortOrder
			repository.getGames(platform, sortOrder)
		}.cachedIn(viewModelScope)

	private val _isRefreshing = MutableStateFlow(false)
	val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

	fun onRefresh() {
		viewModelScope.launch {
			_isRefreshing.value = true
			// 在实际应用中，你可能需要调用 repository.refreshGames(_platform.value)
			// 或者让 PagingSource 失效来真正地触发网络刷新。
			// 这里我们仅模拟UI状态。
			kotlinx.coroutines.delay(1500)
			_isRefreshing.value = false
		}
	}
}