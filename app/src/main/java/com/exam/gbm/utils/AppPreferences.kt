package com.exam.gbm.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Provide preferences access to all app
 *
 * @author Rigoberto Torres on 25/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
object AppPreferences {

    const val NAME = "GBMPreferences"
    private lateinit var preferences: SharedPreferences

    /**
     * Initializes the shared preferences.
     * @param context the application context
     */
    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    /**
     * Apply the editor changes for the [SharedPreferences] class
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    /**
     * Clean data for the [SharedPreferences] class
     */
    fun clearPreferences() {
        preferences.edit().clear().commit()
    }

    /** Storage the if only use pin to security*/
    var biometricPreference: Boolean
        get() = preferences.getBoolean(Constants.IS_BIOMETRIC, false)
        set(value) = preferences.edit {
            it.putBoolean(Constants.IS_BIOMETRIC, value)
        }

    /** Storage the if only use pin to security*/
    var isLogged: Boolean
        get() = preferences.getBoolean(Constants.IS_LOGGED, false)
        set(value) = preferences.edit {
            it.putBoolean(Constants.IS_LOGGED, value)
        }


}