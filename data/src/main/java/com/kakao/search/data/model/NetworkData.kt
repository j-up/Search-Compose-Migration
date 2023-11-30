package com.kakao.search.data.model

import com.kakao.search.domain.model.Resource
import retrofit2.Response

inline fun <reified T: Any, reified R: Any> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> try {
            Resource.Success(transform(data))
        } catch (e:Exception) {
            Resource.Failure(e.message ?: "")
        }
        is Resource.Failure -> Resource.Failure("")
    }
}

suspend inline fun <T> getRemoteResult(crossinline call: suspend () -> Response<T>): Resource<T> {
    try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) return Resource.Success(body)
        }
        return error("Failed to load data.")
    } catch (e: Exception) {
        return error(e.message ?: e.toString())
    }
}

fun <T> error(errorMsg: String): Resource<T> {
    return Resource.Failure(errorMsg)
}
