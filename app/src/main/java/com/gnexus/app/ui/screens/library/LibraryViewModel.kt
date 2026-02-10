package com.gnexus.app.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.gnexus.app.data.repository.GameRepository

class LibraryViewModel(
    private val repository: GameRepository
) : ViewModel() {
}