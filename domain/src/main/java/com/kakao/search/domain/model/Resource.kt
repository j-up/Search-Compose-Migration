package com.kakao.search.domain.model

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val errorMsg: String) : Resource<Nothing>()
}