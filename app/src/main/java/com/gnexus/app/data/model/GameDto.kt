package com.gnexus.app.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GamesResDto(
    val nextOffset: Int? = null,
    val previousOffset: Int,
    @field:Json(name = "titles")
    val titles: List<GameDto>,
    val totalItemCount: Int
)

@JsonClass(generateAdapter = true)
data class GameDto(
    val category: String,
    @field:Json(name = "concept")
    val concept: Concept,
    val firstPlayedDateTime: String,
    val imageUrl: String,
    val lastPlayedDateTime: String,
    val localizedImageUrl: String,
    val localizedName: String,
    @field:Json(name = "media")
    val media: Media,
    val name: String,
    val playCount: Int,
    val playDuration: String,
    val service: String,
    val sortableName: String,
    val titleId: String
)

@JsonClass(generateAdapter = true)
data class Concept(
    val country: String,
    @field:Json(name = "genres")
    val genres: List<String>,
    val id: Int,
    val language: String,
    @field:Json(name = "localizedName")
    val localizedName: LocalizedName,
    @field:Json(name = "media")
    val media: Media,
    val name: String,
    @field:Json(name = "titleIds")
    val titleIds: List<String>
)

@JsonClass(generateAdapter = true)
data class LocalizedName(
    val defaultLanguage: String,
    @field:Json(name = "metadata")
    val metadata: Any
)

//@JsonClass(generateAdapter = true)
//data class Metadata(
//    val arAE: String,
//    val daDK: String,
//    val deDE: String,
//    val enGB: String,
//    val enUS: String,
//    val es419: String,
//    val esES: String,
//    val fiFI: String,
//    val frCA: String,
//    val frFR: String,
//    val itIT: String,
//    val jaJP: String,
//    val koKR: String,
//    val nlNL: String,
//    val noNO: String,
//    val plPL: String,
//    val ptBR: String,
//    val ptPT: String,
//    val ruRU: String,
//    val svSE: String,
//    val trTR: String,
//    val ukUA: String,
//    val zhHans: String,
//    val zhHant: String
//)

@JsonClass(generateAdapter = true)
data class Image(
    val format: String,
    val type: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class Media(
    @field:Json(name = "audios")
    val audios: List<Any>,
    @field:Json(name = "images")
    val images: List<Image>,
    @field:Json(name = "videos")
    val videos: List<Any>
)