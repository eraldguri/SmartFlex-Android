package com.erald_guri.smartflex_android.view_models

import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.erald_guri.smartflex_android.data.model.ResponseModel
import com.erald_guri.smartflex_android.di.FlexPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val app: Application,
    private val flexPreferences: FlexPreferences
): AndroidViewModel(app) {

    private val _response = MutableLiveData<ResponseModel>()
    val response: LiveData<ResponseModel> = _response

    fun setLanguage(language: String): Context {
        flexPreferences.setLanguage(language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(language)
        } else {
            updateResourcesLegacy(language)
        }
    }

    fun getLanguage(): String? = flexPreferences.getLanguage()

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = app.applicationContext.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return app.applicationContext.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val appResources = app.applicationContext.resources

        val configuration = app.applicationContext.resources.configuration
        configuration.locale = locale

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        appResources.updateConfiguration(configuration, appResources.displayMetrics)

        return app.applicationContext
    }

}