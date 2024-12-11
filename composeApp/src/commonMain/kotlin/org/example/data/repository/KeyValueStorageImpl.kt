package org.example.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.example.domain.KeyValueStorage

class KeyValueStorageImpl(
    private val dataStore: DataStore<Preferences>
): KeyValueStorage {
    override suspend fun putString(key: String, value: String)  = withContext(Dispatchers.IO){
        try {
            dataStore.updateData { preferences ->
                preferences.toMutablePreferences().apply {
                    set(stringPreferencesKey(key), value)
                }
            }
            println("success")
            true
        }catch (e:Exception){
            println("error $e")
            return@withContext false
        }
    }

    override suspend fun getString(key: String): String? = withContext(Dispatchers.IO)  {
        val result = dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)]
        }.first()

        println("result $result")
        return@withContext result


    }
}