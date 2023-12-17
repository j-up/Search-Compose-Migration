package com.kakao.search.screens.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kakao.search.navigation.NavigationConst
import com.kakao.search.screens.bookmark.BookmarkPresentation
import com.kakao.search.screens.bookmark.BookmarkScreen
import com.kakao.search.screens.bookmark.BookmarkViewModel
import com.kakao.search.screens.search.SearchScreen
import com.kakao.search.screens.search.SearchViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalMaterialApi
@InternalCoroutinesApi
@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        MainScreenNavigation(navController = navController, paddingValues = innerPadding)
    }
}

@Composable
private fun MainScreenNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = NavigationConst.Search.route) {
        composable(NavigationConst.Search.route) {
            InitSearchScreen(paddingValues, navController)
        }

        composable(NavigationConst.Bookmark.route) {
            InitBookmarkScreen(paddingValues)
        }
    }
}

@Composable
private fun InitSearchScreen(paddingValues: PaddingValues, navController: NavHostController) {
    val searchViewModel: SearchViewModel = hiltViewModel()
    SearchScreen(
        state = searchViewModel.searchState.value,
        paddingValues = paddingValues,
        onFetchMediaEvent = {
            searchViewModel.stateClear()
            searchViewModel.fetchMediaThumbnails(it, SearchViewModel.START_PAGE)
        },
        onBookmarkClickListener = {
            searchViewModel.updateBookmark(it)
        },
        onBookmarkScreenButtonListener = {
            navController.navigate(NavigationConst.Bookmark.route)
        }
    )
}

@Composable
private fun InitBookmarkScreen(paddingValues: PaddingValues) {
    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

    BookmarkScreen(
        state = bookmarkViewModel.bookmarkState.value,
        paddingValues = paddingValues,
        onBookmarkClickListener = {
            if (it is BookmarkPresentation.ImagePresent) {
                bookmarkViewModel.deleteBookmark(it.thumbnail)
            }
        }
    )

    bookmarkViewModel.fetchDataStoreBookmark()
}