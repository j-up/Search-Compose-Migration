package com.kakao.search.data.model.remote

import com.kakao.search.domain.model.remote.KakaoImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


sealed class KakaoResponse {
    @Serializable
    data class Meta(
        @SerialName("total_count") val totalCount: Int = 0,
        @SerialName("pageable_count") val pageableCount: Int = 0,
        @SerialName("is_end") val isEnd: Boolean = true
    )

    @Serializable
    data class ImageDocument(
        val collection: String = "",
        @SerialName("thumbnail_url") val thumbnailUrl: String = "",
        @SerialName("image_url") val imageUrl: String = "",
        val width: Int = 0,
        val height: Int = 0,
        @SerialName("display_sitename") val displaySiteName: String = "",
        @SerialName("doc_url") val docUrl: String = "",
        val datetime: String = ""
    )

    @Serializable
    data class VideoDocument(
        val title: String = "",
        @SerialName("play_time") val playTime: Int = 0,
        val thumbnail: String = "",
        val url: String = "",
        val datetime: String = "",
        val author: String = ""
    )

    @Serializable
    data class ImageResponse(val meta: Meta = Meta(), val documents: List<ImageDocument> = listOf()) : KakaoResponse() {
        fun mapToDomain() = documents.map {
            KakaoImage(thumbnail = it.thumbnailUrl, dateTime = it.datetime)
        }
    }

    @Serializable
    data class VideoResponse(val meta: Meta = Meta(), val documents: List<VideoDocument> = listOf()) : KakaoResponse() {
        fun mapToDomain() = documents.map {
            KakaoImage(thumbnail = it.thumbnail, dateTime = it.datetime)
        }
    }
}