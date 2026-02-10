package com.gnexus.app.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gnexus.app.data.api.GameApiService
import com.gnexus.app.data.db.AppDatabase
import com.gnexus.app.data.db.GameEntity
import com.gnexus.app.data.mediator.GameRemoteMediator
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class GameRepository(
    private val platform: IntArray,
    private val gameApiService: GameApiService,
    private val db: AppDatabase,
    useFake: Boolean = true
) {
    fun games(): Flow<PagingData<GameEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = GameRemoteMediator(platform, gameApiService, db),
            pagingSourceFactory = { db.gameDao().pagingSource() },
        ).flow
    }
}