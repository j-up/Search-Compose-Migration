package com.kakao.search.data.repository.remote

import com.kakao.search.data.model.map
import com.kakao.search.data.source.remote.KaKaoDataSource
import com.kakao.search.domain.model.Resource
import com.kakao.search.domain.model.remote.KakaoImage
import com.kakao.search.domain.repository.remote.KakaoRepository
import javax.inject.Inject


class KakaoRepositoryImpl @Inject constructor(
    private val kakaoDataSource: KaKaoDataSource,
) : KakaoRepository {
    override suspend fun getSearchImage(query: String, page: Int): Resource<List<KakaoImage>> {
        return kakaoDataSource.getSearchImage(query, page).map {
            it.mapToDomain()
        }
    }

    override suspend fun getSearchVideo(query: String, page: Int): Resource<List<KakaoImage>> {
        return kakaoDataSource.getSearchVideo(query, page).map {
            it.mapToDomain()
        }
    }
}