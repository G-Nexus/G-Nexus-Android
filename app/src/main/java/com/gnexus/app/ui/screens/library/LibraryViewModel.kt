package com.gnexus.app.ui.screens.library

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.gnexus.app.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
	private val repository: GameRepository,
	private val savedStateHandle: SavedStateHandle
) : ViewModel() {
	private val _platform = MutableStateFlow("psn") // 默认加载 PSN 数据

	/**
	 * 当用户切换Tab时，由UI调用此方法来更新平台。
	 */
	fun onPlatformChanged(newPlatform: String) {
		_platform.value = newPlatform
	}

	@OptIn(ExperimentalCoroutinesApi::class)
	val games = _platform.flatMapLatest { platform ->
		repository.getGames(platform)
	}.cachedIn(viewModelScope) // cachedIn 确保在配置变更（如旋转屏幕）后数据得以保留

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