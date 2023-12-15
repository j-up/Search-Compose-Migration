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
            InitSearchScreen(paddingValues)
        }

        composable(NavigationConst.Bookmark.route) {
            InitBookmarkScreen(paddingValues)
        }
    }
}

@Composable
private fun InitSearchScreen(paddingValues: PaddingValues) {
    val searchViewModel: SearchViewModel = hiltViewModel()
    SearchScreen(
        state = searchViewModel.searchState.value,
        paddingValues = paddingValues,
        onFetchMediaEvent = {
            searchViewModel.fetchMediaThumbnails(it, SearchViewModel.START_PAGE)
        },
        onBookmarkClickListener = {
            searchViewModel.updateBookmark(it)
        }
    )
}

@Composable
private fun InitBookmarkScreen(paddingValues: PaddingValues) {

}