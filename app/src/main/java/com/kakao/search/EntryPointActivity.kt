package com.kakao.search

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent

import android.os.Bundle
import androidx.compose.material.ExperimentalMaterialApi
import com.kakao.search.screens.main.MainScreen
import com.kakao.search.theme.Search
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class EntryPointActivity : AppCompatActivity() {

    @OptIn(InternalCoroutinesApi::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Search.SearchTheme {
                MainScreen()
            }
        }
    }
}