package com.kakao.search.screens.search

import com.kakao.search.screens.search.adapter.SearchPresentation

sealed class SearchState {
    object OnClear: SearchState()
    object OnFail: SearchState()
    data class OnImageListLoad(val list: List<SearchPresentation>): SearchState()
}