package com.kakao.search.domain.repository.remote

import com.kakao.search.domain.model.Resource
import com.kakao.search.domain.model.remote.KakaoImage
import kotlinx.coroutines.flow.Flow

interface KakaoRepository {
    suspend fun getSearchImage(query: String, page: Int): Resource<List<KakaoImage>>
    suspend fun getSearchVideo(query: String, page: Int): Resource<List<KakaoImage>>
}