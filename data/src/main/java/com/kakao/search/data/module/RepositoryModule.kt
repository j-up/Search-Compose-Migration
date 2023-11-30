package com.kakao.search.data.module

import com.kakao.search.data.repository.remote.KakaoRepositoryImpl
import com.kakao.search.domain.repository.remote.KakaoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindKakaoRepository(impl: KakaoRepositoryImpl): KakaoRepository
}

