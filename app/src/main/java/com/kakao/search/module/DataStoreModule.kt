package com.kakao.search.module

import android.content.Context
import com.kakao.search.datastore.BookmarkDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Singleton
    @Provides
    fun provideOauthDataStore(
        @ApplicationContext context: Context
    ): BookmarkDataStore = BookmarkDataStore(context)
}