package com.gnexus.app.data.mappers

import com.gnexus.app.data.db.GameEntity
import com.gnexus.app.data.model.GameDto

fun GameDto.toGameEntity(): GameEntity {
    return GameEntity(
	    titleId = titleId,
	    sortableName =sortableName,
	    service = service,
	    playDuration = playDuration,
	    playCount = playCount,
	    name = name,
	    localizedName = localizedName,
	    localizedImageUrl = localizedImageUrl,
	    lastPlayedDateTime = lastPlayedDateTime,
	    imageUrl = imageUrl,
	    firstPlayedDateTime = firstPlayedDateTime
    )
}