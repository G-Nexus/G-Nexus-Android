package com.gnexus.app.data.api

import com.gnexus.app.data.model.GameDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApiService {
    @GET("games")
    suspend fun getUserLibraryGames(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 20,
    ): List<GameDto>
}