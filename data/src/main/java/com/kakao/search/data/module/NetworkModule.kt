package com.kakao.search.data.module

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kakao.search.data.BuildConfig
import com.kakao.search.data.api.remote.KakaoService
import com.kakao.search.data.common.REMOTE_TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val client = OkHttpClient.Builder()
            .readTimeout(REMOTE_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(REMOTE_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REMOTE_TIMEOUT, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(logging)
        }

        return client.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val json = Json {
            coerceInputValues = true
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true
        }

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.KAKAO_BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideKakaoService(retrofit: Retrofit): KakaoService {
        return retrofit.create(KakaoService::class.java)
    }
}