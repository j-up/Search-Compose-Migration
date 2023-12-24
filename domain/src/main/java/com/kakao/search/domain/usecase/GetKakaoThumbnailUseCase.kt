package com.kakao.search.domain.usecase

import com.kakao.search.domain.common.REMOTE_TIMEOUT
import com.kakao.search.domain.model.Resource
import com.kakao.search.domain.model.remote.KakaoImage
import com.kakao.search.domain.repository.remote.KakaoRepository
import kotlinx.coroutines.*
import java.io.IOException
import javax.inject.Inject

class GetKakaoThumbnailUseCase @Inject constructor(
    private val kakaoRepository: KakaoRepository
    ) {

    private val supervisor = SupervisorJob()

    suspend fun invoke(
        query: String,
        page: Int,
        scope: CoroutineScope,
    ): Result {
        var list: List<Result> = emptyList()

        val videoDeferred = scope.async(Dispatchers.IO + supervisor) {
            try {
                when (val videoList = kakaoRepository.getSearchVideo(query, page)) {
                    is Resource.Success -> {
                        Result.Success(videoList.data.sorted())
                    }

                    is Resource.Failure -> {
                        Result.Failure
                    }
                }
            } catch (e: Exception) {
                Result.Failure
            }
        }

        val imageDeferred = scope.async(Dispatchers.IO + supervisor) {
            try {
                when (val imageList = kakaoRepository.getSearchImage(query, page)) {
                    is Resource.Success -> {
                        Result.Success(imageList.data.sorted())
                    }
                    is Resource.Failure -> Result.Failure
                }
            } catch (e: Exception) {
                Result.Failure
            }
        }

        try {
            withTimeout(REMOTE_TIMEOUT) {
                list = awaitAll(videoDeferred, imageDeferred)
            }
        } catch (e: TimeoutCancellationException) {
            return Result.Failure
        }

        val imageList: List<Result.Success> = list.filterIsInstance<Result.Success>()

        return when (imageList.isEmpty()) {
            true -> Result.Failure

            else -> Result.Success(imageList.flatMap { it.list })
        }
    }

    sealed class Result {
        data class Success(val list: List<KakaoImage>) : Result()
        object Failure : Result()
    }
}