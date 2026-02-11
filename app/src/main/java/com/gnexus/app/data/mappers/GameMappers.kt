package com.gnexus.app.data.mappers

import com.gnexus.app.data.db.GameEntity
import com.gnexus.app.data.model.GameDto

fun GameDto.toGameEntity(): GameEntity {
    return GameEntity(
        id = id,
        name = name,
        developerPublisher = developerPublisher,
        description = description,
        coverUrl = coverUrl,
        isOwned = isOwned,
        playTime = playTime,
        progress = progress,
        platform = platform,
        trophyCount = trophyCount,
        lastPlayedAt = lastPlayedAt
    )
}

fun GameEntity.toGameDto(): GameDto {
    return GameDto(
        id = id,
        name = name,
        developerPublisher = developerPublisher,
        description = description,
        coverUrl = coverUrl,
        isOwned = isOwned,
        playTime = playTime,
        progress = progress,
        platform = platform,
        trophyCount = trophyCount,
        lastPlayedAt = lastPlayedAt
    )
}