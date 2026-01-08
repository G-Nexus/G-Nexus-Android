package com.gnexus.app.data.api

import com.gnexus.app.data.model.GameDto
import retrofit2.http.GET
import retrofit2.http.Path

/**
    网络接口定义 (Retrofit Interface)
 **/
interface GameApiService {
    @GET("user/library")
    suspend fun getLibraryGames(): List<GameDto>
}