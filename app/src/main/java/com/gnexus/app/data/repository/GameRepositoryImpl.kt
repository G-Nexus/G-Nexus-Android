package com.gnexus.app.data.repository

import com.gnexus.app.data.api.GameApiService
import com.gnexus.app.data.model.GameDto
import com.gnexus.app.domain.model.MetaGame

class GameRepositoryImpl(private val api: GameApiService) : GameApiService {
    override suspend fun getLibraryGames(): List<GameDto> {
        TODO("Not yet implemented")
    }

}