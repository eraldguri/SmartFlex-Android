package com.erald_guri.smartflex_android.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlexPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences = context.getSharedPreferences("com.smartflex", Context.MODE_PRIVATE)

    fun getLanguage(): String? = sharedPreferences.getString(PREF_LANGUAGE, "")

    fun setLanguage(query: String) {
        sharedPreferences.edit().putString(PREF_LANGUAGE, query).apply()
    }

    companion object {
        const val PREF_LANGUAGE = "language"
    }

}