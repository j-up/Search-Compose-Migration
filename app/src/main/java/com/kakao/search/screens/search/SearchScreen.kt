package com.kakao.search.screens.search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kakao.search.R
import com.kakao.search.domain.model.remote.KakaoImage
import com.kakao.search.theme.Search.LocalColors

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    state: SearchState,
    onFetchMediaEvent: (String) -> Unit,
    onBookmarkClickListener: (KakaoImage) -> Unit,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    var text by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
    val onImeAction = {
        onFetchMediaEvent(text)
        keyboardController?.hide()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues)
            .background(LocalColors.current.background)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                value = text,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = androidx.compose.ui.text.input.ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onImeAction()
                    }
                ),
                onValueChange = { newText -> text = newText },
                placeholder = { Text("검색어를 입력하세요") },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
            )

            Button(modifier = Modifier
                .padding(start = 8.dp), onClick = { /*TODO*/ }) {
                Text("북마크")
            }
        }

        when(state) {
            is SearchState.OnImageListLoad -> {
                KakaoMediaList(state.list, onBookmarkClickListener)
            }

            SearchState.OnClear -> {

            }

            SearchState.OnFail -> {
            }
        }
    }
}

@Composable
fun KakaoMediaList(list: List<SearchPresentation>, onBookmarkClickListener: (KakaoImage) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(list) {
            KaKaoMediaItemRow(it, onBookmarkClickListener)
        }
    }
}

@Composable
fun KaKaoMediaItemRow(present: SearchPresentation, onBookmarkClickListener: (KakaoImage) -> Unit) {
    when(present) {
        is SearchPresentation.ImagePresent -> {
            var isBookmarked by remember { mutableStateOf(present.kakaoImage.isBookmark) }

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

                    KaKaoMediaItemImageContent(present)

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = when (isBookmarked) {
                            true -> painterResource(R.drawable.ic_baseline_bookmark_24)
                            else -> painterResource(R.drawable.ic_baseline_bookmark_border_24)
                        },
                        contentDescription = "bookmark",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                isBookmarked = !isBookmarked
                                onBookmarkClickListener(present.kakaoImage)
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun KaKaoMediaItemImageContent(imagePresent: SearchPresentation.ImagePresent) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = imagePresent.kakaoImage.thumbnail,
            contentDescription = "kakaoImage thumbnail",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(text = imagePresent.kakaoImage.dateToString(), modifier = Modifier.padding(top = 5.dp))
    }
}