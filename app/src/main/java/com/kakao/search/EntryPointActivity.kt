package com.kakao.search

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent

import android.os.Bundle
import com.kakao.search.screens.main.MainScreen
import com.kakao.search.theme.Search
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryPointActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Search.SearchTheme {
                MainScreen()
            }
        }
    }
}