package com.kakao.search.screens.search.adapter

import com.kakao.search.domain.model.remote.KakaoImage

sealed class SearchPresentation {
    enum class Type {
        Image, PageNumber
    }

    data class ImagePresent(val kakaoImage: KakaoImage): SearchPresentation()

    data class PageNumberPresent(val page: Int): SearchPresentation()
}