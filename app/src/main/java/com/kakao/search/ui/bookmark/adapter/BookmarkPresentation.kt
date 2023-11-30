package com.kakao.search.ui.bookmark.adapter

sealed class BookmarkPresentation {
    enum class Type {
        Image
    }

    data class ImagePresent(val thumbnail: String): BookmarkPresentation()
}