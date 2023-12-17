package com.kakao.search.screens.bookmark

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kakao.search.R
import com.kakao.search.theme.Search.LocalColors

@Composable
fun BookmarkScreen(
    state: BookmarkState,
    onBookmarkClickListener: (BookmarkPresentation) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues)
            .background(LocalColors.current.background)
    ) {
        when(state) {
            is BookmarkState.OnBookmarkListLoad -> {
                KakaoMediaBookmarkList(state.list, onBookmarkClickListener)
            }

            BookmarkState.OnClear -> {

            }
        }
    }
}

@Composable
fun KakaoMediaBookmarkList(list: MutableList<BookmarkPresentation>, onBookmarkClickListener: (BookmarkPresentation) -> Unit) {
    val bookmarkList = remember {
        mutableStateListOf<BookmarkPresentation>().apply {
            addAll(list)
        }
    }
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(bookmarkList) { itemPresent ->
            KaKaoMediaBookmarkItemRow(itemPresent) { bookmarkPresentation ->
                bookmarkList.remove(bookmarkPresentation)
                onBookmarkClickListener(bookmarkPresentation)
            }
        }
    }
}

@Composable
fun KaKaoMediaBookmarkItemRow(present: BookmarkPresentation, onBookmarkClickListener: (BookmarkPresentation) -> Unit) {
    when(present) {
        is BookmarkPresentation.ImagePresent -> {
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .animateContentSize()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Spacer(modifier = Modifier.weight(1f))

                    KaKaoMediaBookmarkItemImage(present)

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(ICON_BOOKMARK_ON),
                        contentDescription = "bookmark",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onBookmarkClickListener(present)
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun KaKaoMediaBookmarkItemImage(imagePresent: BookmarkPresentation.ImagePresent) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = imagePresent.thumbnail,
            contentDescription = "kakaoImage thumbnail",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

const val ICON_BOOKMARK_ON = R.drawable.ic_baseline_bookmark_24
