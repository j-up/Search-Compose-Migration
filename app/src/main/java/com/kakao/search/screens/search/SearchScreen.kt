package com.kakao.search.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchScreen(state: SearchState,
                 paddingValues: PaddingValues,
                 modifier: Modifier = Modifier) {

    var text by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(paddingValues)
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
    }
}