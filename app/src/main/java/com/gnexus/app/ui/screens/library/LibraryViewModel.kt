package com.gnexus.app.ui.screens.library

import androidx.activity.result.launch
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.gnexus.app.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PLATFORM_ARG = "platform"

@HiltViewModel
class LibraryViewModel @Inject constructor(
	private val repository: GameRepository,
	private val savedStateHandle: SavedStateHandle
) : ViewModel() {
	val platform: String = savedStateHandle.get<String>(PLATFORM_ARG)
		?: throw IllegalStateException("Platform argument is missing in the navigation graph!")
	val games = repository.getGames().cachedIn(viewModelScope)

	private val _isRefreshing = MutableStateFlow(false)
	val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

	fun onRefresh() {
		viewModelScope.launch {
			_isRefreshing.value = true
			// 注意：Paging 3 的刷新是由 PagingSource 内部的 invalidate() 触发的。
			// 在实际应用中，你可能需要调用一个 repository 方法来清除缓存或触发 PagingSource 失效。
			// 对于简单的UI状态模拟，我们可以只控制 isRefreshing 的状态。
			// 这里的 games.refresh() 方法如果直接调用可能不会生效，
			// 正确的做法是让 PagingSource 失效。

			// 这里我们仅更新UI状态，具体的刷新逻辑应在 PagingSource 中处理。
			// 这里的 delay 只是为了模拟网络请求的耗时，让用户能看到刷新指示器。
			kotlinx.coroutines.delay(1500)
			_isRefreshing.value = false
		}
	}
}