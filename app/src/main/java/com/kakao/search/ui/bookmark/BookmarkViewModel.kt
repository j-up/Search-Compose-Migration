package com.kakao.search.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.search.datastore.BookmarkDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookmarkViewModel @Inject constructor(
    val bookmarkDataStore: BookmarkDataStore
) : ViewModel() {

    fun deleteBookmark(thumbnail: String) = viewModelScope.launch(Dispatchers.IO) {
            bookmarkDataStore.updateBookmark(thumbnail, false)
        }
}