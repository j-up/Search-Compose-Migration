package com.kakao.search.screens.search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kakao.search.R
import com.kakao.search.screens.search.adapter.SearchPresentation
import com.kakao.search.theme.Search.LocalColors

@Composable
fun SearchScreen(
    state: SearchState,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    var text by remember { mutableStateOf("") }

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

        KakaoMediaList(listOf())
    }
}

@Composable
fun KakaoMediaList(list: List<SearchPresentation>) {
    val stringList = listOf<String>("a", "b", "c", "d", "e", "f")

    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(stringList) {
            KaKaoMediaItemRow()
        }
    }
}

@Composable
fun KaKaoMediaItemRow() {
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

            KaKaoMediaItemImageContent()

            Spacer(modifier = Modifier.weight(1f))
            
            Image(
                painter = painterResource(R.drawable.ic_baseline_bookmark_border_24),
                contentDescription = "bookmark",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* 북마크 클릭 시 수행할 작업 */ }
            )
        }
    }
}

@Composable
fun KaKaoMediaItemImageContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.sample_icon),
            contentDescription = "image",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(text = "타이틀", modifier = Modifier.padding(top = 5.dp))
    }
}