package com.kakao.search.data.api.remote

import com.kakao.search.data.BuildConfig
import com.kakao.search.data.model.remote.KakaoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoService {
    @GET("v2/search/image")
    suspend fun getSearchImage(
        @Header("Authorization") authorization: String = BuildConfig.KAKAO_ACCESS_KEY,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = SORT_KEY_RECENCY,
        @Query("size") size: Int = DEFAULT_SIZE,
    ): Response<KakaoResponse.ImageResponse>

    @GET("v2/search/vclip")
    suspend fun getSearchVideo(
        @Header("Authorization") authorization: String = BuildConfig.KAKAO_ACCESS_KEY,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = SORT_KEY_RECENCY,
        @Query("size") size: Int = DEFAULT_SIZE,
    ): Response<KakaoResponse.VideoResponse>
}

private const val SORT_KEY_RECENCY = "recency"
private const val DEFAULT_SIZE = 50