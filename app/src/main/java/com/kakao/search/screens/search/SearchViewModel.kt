package com.kakao.search.screens.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.search.datastore.BookmarkDataStore
import com.kakao.search.domain.model.remote.KakaoImage
import com.kakao.search.domain.usecase.GetKakaoThumbnailUseCase
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

                    _searchState.value = SearchState.OnImageListLoad(resultList)
                }

                is GetKakaoThumbnailUseCase.Result.Failure -> {
                    _searchState.value = SearchState.OnFail
                }

            }
        }

    }

    fun updateBookmark(kakaoImage: KakaoImage) = viewModelScope.launch(Dispatchers.IO) {
        if (_searchState.value is SearchState.OnImageListLoad) {

            val updateList = (_searchState.value as SearchState.OnImageListLoad).list.map { present ->
                when {
                    present is SearchPresentation.ImagePresent && present.kakaoImage == kakaoImage -> {
                        present.copy(kakaoImage.copy(isBookmark = !kakaoImage.isBookmark))
                    }

                    else -> present
                }
            }

            _searchState.value = SearchState.OnImageListLoad(updateList)
        }

        bookmarkDataStore.updateBookmark(kakaoImage.thumbnail, !kakaoImage.isBookmark)
    }

    fun stateClear() = viewModelScope.launch(Dispatchers.IO) {
        _searchState.value = SearchState.OnClear
    }

    companion object {
        const val START_PAGE = 1
    }
}