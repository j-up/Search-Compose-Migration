package com.kakao.search.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkDataStore @Inject constructor(@ApplicationContext val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = BOOKMARK_DATA_STORE_NAME)

    val bookmarkFlow: Flow<Map<String, Boolean>> = context.dataStore.data
        .catch {
            emit(emptyPreferences())
        }.map { preferences ->
            preferences.asMap().map { (key, value) ->
                key.name to (value as? Boolean ?: false)
            }.toMap()
        }

    suspend fun updateBookmark(thumbnail: String, isBookmark: Boolean) {
        context.dataStore.edit {
            when(isBookmark) {
                true -> it[booleanPreferencesKey(thumbnail)] = true
                false -> it.remove(booleanPreferencesKey(thumbnail))
            }
        }
    }

    companion object {
        private const val BOOKMARK_DATA_STORE_NAME = "Bookmark"
    }
}