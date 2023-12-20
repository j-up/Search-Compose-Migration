package com.kakao.search.screens.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.search.datastore.BookmarkDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkDataStore: BookmarkDataStore
) : ViewModel() {

    private val _bookmarkStateFlow: MutableStateFlow<BookmarkState> = MutableStateFlow(BookmarkState.OnClear)
    val bookmarkStateFlow: Flow<BookmarkState> = _bookmarkStateFlow.asStateFlow()

    init {
        viewModelScope.launch (Dispatchers.IO) {
            bookmarkDataStore.bookmarkFlow.collect { bookmarkMap ->
                val list = bookmarkMap.filter {
                    it.value
                }.map {
                    BookmarkPresentation.ImagePresent(it.key)
                }

                _bookmarkStateFlow.emit(BookmarkState.OnBookmarkListLoad(list.reversed().toMutableList()))
            }
        }
    }

    fun deleteBookmark(thumbnail: String) = viewModelScope.launch(Dispatchers.IO) {
            bookmarkDataStore.updateBookmark(thumbnail, false)
    }
}