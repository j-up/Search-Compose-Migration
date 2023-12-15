package com.kakao.search.screens.search

sealed class SearchState {
    object OnClear: SearchState()
    object OnFail: SearchState()
    data class OnImageListLoad(val list: List<SearchPresentation>): SearchState()
}