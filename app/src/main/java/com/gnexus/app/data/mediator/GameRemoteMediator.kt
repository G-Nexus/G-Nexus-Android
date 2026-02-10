package com.gnexus.app.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.gnexus.app.data.api.GameApiService
import com.gnexus.app.data.db.AppDatabase
import com.gnexus.app.data.db.GameEntity
import com.gnexus.app.data.db.GameRemoteKeys

@OptIn(ExperimentalPagingApi::class)
class GameRemoteMediator(
    private val platform: IntArray,
    private val api: GameApiService,
    private val db: AppDatabase
) : RemoteMediator<Int, GameEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GameEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(true)
                db.gameRemoteKeysDao().remoteKeysByGameId(lastItem.id)?.nextKey
                    ?: return MediatorResult.Success(true)
            }
        }
        return try {
            val response = api.getUserLibraryGames(platform, page, state.config.pageSize)

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.gameRemoteKeysDao().clearRemoteKeys()
                    db.gameDao().clearAll()
                }
                val keys = response.map {
                    GameRemoteKeys(
                        gameId = it.id,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (response.isEmpty()) null else page + 1
                    )
                }
                db.gameRemoteKeysDao().insertAll(keys)
                db.gameDao().insertAll(
                    response.map {
                        GameEntity(
                            id = it.id,
                            name = it.name,
                            developerPublisher = it.developerPublisher,
                            description = it.description,
                            coverUrl = it.coverUrl,
                            isOwned = it.isOwned,
                            playTime = it.playTime.toInt(),
                            progress = it.progress.toFloat(),
                            trophyCount = it.trophyCount.toString(),
                            lastPlayedAt = it.lastPlayedAt.toInt(),
                            platform = it.platform.id
                        )
                    }
                )
            }
            MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}