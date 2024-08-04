package com.theapplicationpad.fosst_carapp.Mvvm.PreferenceDatabase

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class UserPreferences(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val USERNAME = stringPreferencesKey("username")
        val EMAIL = stringPreferencesKey("email")
        val PROFILE_PIC = stringPreferencesKey("profile_pic")
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN] ?: false
    }

    suspend fun saveUser(username: String, email: String, profilePic: String) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[USERNAME] = username
            preferences[EMAIL] = email
            preferences[PROFILE_PIC] = profilePic
        }
    }

    suspend fun clearUser() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    val userName: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USERNAME]
    }

    val userEmail: Flow<String?> = dataStore.data.map { preferences ->
        preferences[EMAIL]
    }

    val userProfilePic: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PROFILE_PIC]
    }
}
