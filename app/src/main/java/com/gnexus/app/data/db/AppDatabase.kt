package com.gnexus.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GameEntity::class, GameRemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val gameDao: GameDao
    abstract val gameRemoteKeysDao: GameRemoteKeysDao
}