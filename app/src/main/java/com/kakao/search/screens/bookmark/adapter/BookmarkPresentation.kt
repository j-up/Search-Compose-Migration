package com.kakao.search.screens.bookmark.adapter

sealed class BookmarkPresentation {
    enum class Type {
        Image
    }

    data class ImagePresent(val thumbnail: String): BookmarkPresentation()
}