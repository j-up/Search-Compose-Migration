package com.kakao.search.data.source.remote

import com.kakao.search.data.api.remote.KakaoService
import com.kakao.search.data.model.getRemoteResult
import javax.inject.Inject

class KaKaoDataSource @Inject constructor(
    private val kakaoService: KakaoService
) {
    suspend fun getSearchImage(
        query: String,
        page: Int
    ) = getRemoteResult {
        kakaoService.getSearchImage(query = query, page = page)
    }

    suspend fun getSearchVideo(
        query: String,
        page: Int
    ) = getRemoteResult {
        kakaoService.getSearchVideo(query = query, page = page)
    }
}