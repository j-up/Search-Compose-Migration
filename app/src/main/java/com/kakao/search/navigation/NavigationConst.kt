package com.kakao.search.navigation

sealed class NavigationConst(
    val route: String
) {
    object Search : NavigationConst(
        "Search"
    )

    object Bookmark : NavigationConst(
        "Bookmark"
    )
}