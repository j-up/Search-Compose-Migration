package com.kakao.search.screens.bookmark

sealed class BookmarkState {
    object OnClear: BookmarkState()
    data class OnBookmarkListLoad(val list: MutableList<BookmarkPresentation>): BookmarkState()
}