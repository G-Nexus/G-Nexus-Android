package com.gnexus.app.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gnexus.app.data.db.AppDatabase
import com.gnexus.app.data.db.GameEntity
import com.gnexus.app.data.mediator.GameRemoteMediator
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class GameRepository @Inject constructor(
    private val mediator: GameRemoteMediator,
    private val db: AppDatabase,
) {
    fun getGames(platform: String): Flow<PagingData<GameEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = mediator,
            pagingSourceFactory = { db.gameDao.pagingSource() },
        ).flow
    }
}