package com.gnexus.app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val titleId: String,
    val sortableName: String,
    val service: String,
    val playDuration: String,
    val playCount: Int,
    val name: String,
    val localizedName: String,
    val localizedImageUrl: String,
    val lastPlayedDateTime: String,
    val imageUrl: String,
    val firstPlayedDateTime: String,
)
