package com.gnexus.app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_remote_keys")
data class GameRemoteKeys(
    @PrimaryKey val gameId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)