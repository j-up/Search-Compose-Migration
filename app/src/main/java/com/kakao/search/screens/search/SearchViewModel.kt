package com.kakao.search.screens.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.search.datastore.BookmarkDataStore
import com.kakao.search.domain.model.remote.KakaoImage
import com.kakao.search.domain.usecase.GetKakaoThumbnailUseCase
import com.kakao.search.screens.search.adapter.SearchPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getKakaoThumbnailUseCase: GetKakaoThumbnailUseCase,
    private val bookmarkDataStore: BookmarkDataStore,
) : ViewModel() {

    private val _searchState: MutableState<SearchState> = mutableStateOf(SearchState.OnClear)
    val searchState: State<SearchState> = _searchState as State<SearchState>

    private var fetchMediaJob: Job? = null

    private val list: MutableList<SearchPresentation> = mutableListOf()

    fun fetchMediaThumbnails(query: String, page: Int) {
        if (fetchMediaJob?.isActive == true) {
            fetchMediaJob?.cancel()
            fetchMediaJob = null
        }

        fetchMediaJob = viewModelScope.launch(Dispatchers.IO) {
            val result = getKakaoThumbnailUseCase.invoke(
                query = query,
                page = page,
                bookmarkMap = bookmarkDataStore.bookmarkFlow.first(),
                scope = this
            )

            when (result) {
                is GetKakaoThumbnailUseCase.Result.Success -> {
                    val resultList = result.list.map {
                        SearchPresentation.ImagePresent(
                            KakaoImage(
                                thumbnail = it.thumbnail,
                                dateTime = it.dateTime,
                                isBookmark = it.isBookmark
                            )
                        )
                    }

                    if (list.containsAll(resultList)) {
                        return@launch
                    }

                    list.addAll(resultList)
                    _searchState.value = SearchState.OnImageListLoad(list.toList())
                }

                is GetKakaoThumbnailUseCase.Result.Failure -> {
                    _searchState.value = SearchState.OnFail
                }

            }
        }

    }

    fun updateBookmark(kakaoImage: KakaoImage) = viewModelScope.launch(Dispatchers.IO) {
        bookmarkDataStore.updateBookmark(kakaoImage.thumbnail, !kakaoImage.isBookmark)
    }

    fun stateClear() = viewModelScope.launch(Dispatchers.IO) {
        _searchState.value = SearchState.OnClear
        list.clear()
    }

    companion object {
        const val START_PAGE = 1
    }
}