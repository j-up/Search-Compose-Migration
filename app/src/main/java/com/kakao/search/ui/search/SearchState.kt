package com.kakao.search.ui.search

import com.kakao.search.ui.search.adapter.SearchPresentation

sealed class SearchState {
    object OnClear: SearchState()
    object OnFail: SearchState()
    data class OnImageListLoad(val list: List<SearchPresentation>): SearchState()
}