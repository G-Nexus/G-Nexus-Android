package com.gnexus.app.data.api.fake

import com.gnexus.app.data.api.GameApiService
import com.gnexus.app.data.model.GameDto
import com.gnexus.app.data.model.Platform
import com.gnexus.app.data.model.TrophyCount
import kotlinx.coroutines.delay

class FakeGameApiService : GameApiService {
    override suspend fun getUserLibraryGames(
        platform: IntArray,
        page: Int,
        pageSize: Int
    ): List<GameDto> {
        delay(300)

        return listOf(
            GameDto(
                1, "Game 1", "Developer 1", "Description 1", "cover1.jpg", true, 12.01, 0.3,
                Platform.XBOX, TrophyCount(1, 2, 3, 4), 12
            )
        )
    }
}