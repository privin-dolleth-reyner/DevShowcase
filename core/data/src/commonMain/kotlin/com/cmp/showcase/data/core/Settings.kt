package com.cmp.showcase.data.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath

fun createDataStore(producePath:() -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

internal const val dataStoreFileName = "showcase.preferences_pb"

interface AppSettings{
    fun isDarkThemeEnabled(isSystemInDarkTheme: Boolean = false): Flow<Boolean>
    suspend fun toggleTheme()
}

class AppSettingsImpl(
    private val dataStore: DataStore<Preferences>
): AppSettings{
    private val themeKey = booleanPreferencesKey("dark_mode")

    override fun isDarkThemeEnabled(isSystemInDarkTheme: Boolean): Flow<Boolean> {
        return dataStore.data.map {
            it[themeKey] ?: isSystemInDarkTheme
        }
    }

    override suspend fun toggleTheme() {
        dataStore.edit {
            it[themeKey] = it[themeKey]?.not() ?: false
        }
    }
}