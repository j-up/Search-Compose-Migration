package com.kakao.search.screens.search

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
    val bookmarkDataStore: BookmarkDataStore,
) : ViewModel() {

    private val _searchStateFlow = MutableStateFlow<SearchState>(SearchState.OnClear)
    val searchStateFlow: StateFlow<SearchState> = _searchStateFlow.asStateFlow()

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
                    _searchStateFlow.emit(SearchState.OnImageListLoad(resultList))
                }

                is GetKakaoThumbnailUseCase.Result.Failure -> {
                    _searchStateFlow.emit(SearchState.OnFail)
                }

            }
        }

    }

    fun updateBookmark(kakaoImage: KakaoImage) = viewModelScope.launch(Dispatchers.IO) {
        when (val stateValue = searchStateFlow.value) {
            is SearchState.OnImageListLoad -> {
                val updateList = stateValue.list.filterIsInstance<SearchPresentation.ImagePresent>().map { present ->
                    when {
                        present is SearchPresentation.ImagePresent && present.kakaoImage == kakaoImage -> {
                            present.copy(kakaoImage = kakaoImage.copy(isBookmark = !kakaoImage.isBookmark))
                        }

                        else -> present
                    }
                }

                _searchStateFlow.emit(SearchState.OnImageListLoad(updateList))
            }

            SearchState.OnClear -> {}

            SearchState.OnFail -> {}
        }

        bookmarkDataStore.updateBookmark(kakaoImage.thumbnail, !kakaoImage.isBookmark)
    }

    fun stateClear() = viewModelScope.launch(Dispatchers.IO) {
        _searchStateFlow.emit(SearchState.OnClear)
    }

    companion object {
        const val START_PAGE = 1
    }
}