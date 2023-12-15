package com.kakao.search.screens.search

import com.kakao.search.domain.model.remote.KakaoImage

sealed class SearchPresentation {
    data class ImagePresent(val kakaoImage: KakaoImage): SearchPresentation()
}