package com.gnexus.app.data.mediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import coil3.network.HttpException
import com.gnexus.app.data.api.GameApiService
import com.gnexus.app.data.db.AppDatabase
import com.gnexus.app.data.db.GameEntity
import com.gnexus.app.data.db.GameRemoteKeys
import com.gnexus.app.data.mappers.toGameEntity
import okio.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GameRemoteMediator @Inject constructor(
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
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) {
                    1
                } else {
                    ((lastItem.id / state.config.pageSize) + 1).toInt()
                }
            }
        }
        return try {
            val games = api.getUserLibraryGames(page, state.config.pageSize)
            Log.d("TEST", games.toString())

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.gameRemoteKeysDao.clearRemoteKeys()
                    db.gameDao.clearAll()
                }
                val keys = games.map {
                    GameRemoteKeys(
                        gameId = it.id,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (games.isEmpty()) null else page + 1
                    )
                }
                val gameEntity = games.map { it.toGameEntity() }
                db.gameRemoteKeysDao.insertAll(keys)
                db.gameDao.insertAll(gameEntity)
            }
            MediatorResult.Success(endOfPaginationReached = games.isEmpty())
        } catch (e: IOException) {
            Log.d("TEST", e.toString())

            MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.d("TEST", e.toString())

            MediatorResult.Error(e)
        } catch (e: Exception) {
            Log.d("TEST", e.toString())

            MediatorResult.Error(e)
        }
    }
}