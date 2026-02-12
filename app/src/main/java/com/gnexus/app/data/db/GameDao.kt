package com.gnexus.app.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import dagger.Provides


@Dao
interface GameDao {

    @Upsert
    suspend fun upsertAll(games: List<GameEntity>)

    @Query("SELECT * FROM game ORDER BY lastPlayedAt DESC")
    fun pagingSource(): PagingSource<Int, GameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(games: List<GameEntity>)

    @Query("DELETE FROM game")
    suspend fun clearAll()
}

