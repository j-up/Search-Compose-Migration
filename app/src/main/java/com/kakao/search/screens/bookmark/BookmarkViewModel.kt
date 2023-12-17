package com.kakao.search.screens.bookmark

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.search.datastore.BookmarkDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkDataStore: BookmarkDataStore
) : ViewModel() {

    private val _bookmarkState: MutableState<BookmarkState> = mutableStateOf(BookmarkState.OnClear)
    val bookmarkState: State<BookmarkState> = _bookmarkState as State<BookmarkState>

    fun fetchDataStoreBookmark() = viewModelScope.launch (Dispatchers.IO) {
        bookmarkDataStore.bookmarkFlow.collect { bookmarkMap ->
            val list = bookmarkMap.filter {
                it.value
            }.map {
                BookmarkPresentation.ImagePresent(it.key)
            }

            _bookmarkState.value = BookmarkState.OnBookmarkListLoad(list.reversed().toMutableList())
        }
    }

    fun deleteBookmark(thumbnail: String) = viewModelScope.launch(Dispatchers.IO) {
            bookmarkDataStore.updateBookmark(thumbnail, false)
        }
}