package com.gnexus.app.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gnexus.app.data.db.AppDatabase
import com.gnexus.app.data.db.GameEntity
import com.gnexus.app.data.mediator.GameRemoteMediator
import com.gnexus.app.ui.screens.library.SortOrder
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class GameRepository @Inject constructor(
	private val mediator: GameRemoteMediator,
	private val db: AppDatabase,
) {
	fun getGames(platform: String, sortOrder: SortOrder): Flow<PagingData<GameEntity>> {
		Log.d("GameRepository", "getGames called with platform: $platform")
		return Pager(
			config = PagingConfig(
				pageSize = 20,
				enablePlaceholders = false
			),
			remoteMediator = mediator,
			pagingSourceFactory = { db.gameDao.pagingSource(sortOrder) },
		).flow
	}

	fun getGameByTitleId(titleId: String): Flow<GameEntity?> {
		return db.gameDao.getGameByTitleId(titleId)
	}
}