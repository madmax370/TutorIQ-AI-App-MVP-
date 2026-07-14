package com.tutoriq.android.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tutoriq_prefs")

object PreferencesKeys {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val USER_ID = stringPreferencesKey("user_id")
    val UNIVERSITY = stringPreferencesKey("university")
    val SEMESTER = stringPreferencesKey("semester")
    val COURSE = stringPreferencesKey("course")
    val GEMINI_API_KEY = stringPreferencesKey("gemini_api_key")
}

class DataStoreManager(private val context: Context) {
    
    val authToken: Flow<String?> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN]
        }
    
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTH_TOKEN] = token
        }
    }
    
    suspend fun clearAuthToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.AUTH_TOKEN)
        }
    }
    
    suspend fun saveUserInfo(id: String, name: String, email: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = id
            preferences[PreferencesKeys.USER_NAME] = name
            preferences[PreferencesKeys.USER_EMAIL] = email
        }
    }
    
    val userName: Flow<String?> = context.dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { it[PreferencesKeys.USER_NAME] }
    
    val userEmail: Flow<String?> = context.dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { it[PreferencesKeys.USER_EMAIL] }
    
    suspend fun saveProfile(university: String, semester: String, course: String, apiKey: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.UNIVERSITY] = university
            preferences[PreferencesKeys.SEMESTER] = semester
            preferences[PreferencesKeys.COURSE] = course
            preferences[PreferencesKeys.GEMINI_API_KEY] = apiKey
        }
    }
    
    val profileData: Flow<Map<Preferences.Key<*>, Any?>> = context.dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences ->
            mapOf(
                PreferencesKeys.UNIVERSITY to preferences[PreferencesKeys.UNIVERSITY],
                PreferencesKeys.SEMESTER to preferences[PreferencesKeys.SEMESTER],
                PreferencesKeys.COURSE to preferences[PreferencesKeys.COURSE],
                PreferencesKeys.GEMINI_API_KEY to preferences[PreferencesKeys.GEMINI_API_KEY]
            )
        }
    
    suspend fun clearAllData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
