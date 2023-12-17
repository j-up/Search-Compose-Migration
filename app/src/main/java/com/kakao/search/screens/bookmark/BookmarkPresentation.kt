package com.kakao.search.screens.bookmark

sealed class BookmarkPresentation {
    data class ImagePresent(val thumbnail: String): BookmarkPresentation()
}