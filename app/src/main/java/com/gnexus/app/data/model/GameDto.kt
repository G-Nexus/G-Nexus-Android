package com.gnexus.app.data.model

// Dedicated data class for trophy counts.
//data class TrophyCount(
//    val platinum: Int = 0,
//    val gold: Int = 0,
//    val silver: Int = 0,
//    val bronze: Int = 0
//)

// Use an enum for platforms to ensure type safety.
//enum class Platform(val id: Int) {
//    PLAYSTATION(1),
//    XBOX(2),
//    PC(3),
//    NINTENDO_SWITCH(4);
//    // Add other platforms as needed
//
//    companion object {
//        fun fromId(id: Int): Platform? = entries.find { it.id == id }
//    }
//}

data class GameDto(
    val id: Long,
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