package com.gnexus.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameRemoteKeysDao {
    @Query("SELECT * FROM game_remote_keys WHERE gameId = :id")
    suspend fun remoteKeysByGameId(id: Long): GameRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<GameRemoteKeys>)

    @Query("DELETE FROM game_remote_keys")
    suspend fun clearRemoteKeys()
}