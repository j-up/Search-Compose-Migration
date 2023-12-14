package com.kakao.search.screens.search.adapter

import com.kakao.search.domain.model.remote.KakaoImage

sealed class SearchPresentation {
    enum class Type {
        Image
    }

    data class ImagePresent(val kakaoImage: KakaoImage): SearchPresentation()
}