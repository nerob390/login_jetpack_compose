package com.mostafizur.loginwithjetpackcompose.utils
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.mostafizur.loginwithjetpackcompose.ui.model.User

object AuthUser {

    private const val PREF_NAME = "my_prefs"
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private val gson = Gson() // Create an instance of Gson
    // Initialize the shared preferences
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    // Save String data
    fun saveString(key: String, value: String) {
        editor.putString(key, value).apply()
    }
    // Save a User object
    fun saveUser(user: User) {
        val userJson = gson.toJson(user) // Convert User object to JSON string
        editor.putString("user", userJson).apply()
    }
    // Get a User object
    fun getUser(): User? {
        val userJson = sharedPreferences.getString("user", null) // Retrieve the user JSON string
        return if (userJson != null) {
            gson.fromJson(userJson, User::class.java) // Convert the JSON string back to a User object
        } else {
            null // Return null if the user is not found
        }
    }

    // Save Boolean data
    fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    // Save Int data
    fun saveInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    // Save Long data
    fun saveLong(key: String, value: Long) {
        editor.putLong(key, value).apply()
    }

    // Get String data
    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    // Get Boolean data
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    // Get Int data
    fun getInt(key: String, defaultValue: Int = 0): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    // Get Long data
    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    // Remove a specific key
    fun remove(key: String) {
        editor.remove(key).apply()
    }

    // Clear all preferences
    fun clear() {
        editor.clear().apply()
    }

    // Check if a key exists
    fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }
}
