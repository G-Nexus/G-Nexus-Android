package com.gnexus.app.data.api

import com.gnexus.app.data.model.GameDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * platform [1,2,3] 对应平台数组，当空数组作全平台查询
 */
interface GameApiService {
    @GET("games")
    suspend fun getUserLibraryGames(
        @Query("platform") platform: IntArray,
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 20,
    ): List<GameDto>
}