package com.gnexus.app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val developerPublisher: String, // Could be split into two fields if needed
    val description: String,
    val coverUrl: String,
    val isOwned: Boolean,
    val playTime: Int,          // Use Duration for time measurement
    val progress: Double,             // Ensure this is validated to be between 0.0 and 1.0
    val platform: Int,          // Suggestion 2: Use the Platform enum
    val trophyCount: String,    // Suggestion 1 & 4: Use the new class and a clearer name
    val lastPlayedAt: Int        // Suggestion 5: Use Instant for a point in time
)
