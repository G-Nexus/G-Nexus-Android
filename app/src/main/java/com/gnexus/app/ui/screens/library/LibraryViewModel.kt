package com.gnexus.app.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.gnexus.app.data.db.GameDao
import com.gnexus.app.data.db.GameEntity
import com.gnexus.app.data.mappers.toGameDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val gameDao: GameDao
) : ViewModel() {
    val gamesPagingFlow = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { gameDao.pagingSource() }
    ).flow
        .map { pagingData ->
            pagingData.map { it.toGameDto() }
        }
        .cachedIn(viewModelScope)
}