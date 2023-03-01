package com.erald_guri.smartflex_android.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erald_guri.smartflex_android.data.model.Quote
import com.erald_guri.smartflex_android.data.model.QuotesModel
import com.erald_guri.smartflex_android.utils.JsonUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(val app: Application) : AndroidViewModel(app) {

    private val _quotes = MutableLiveData<ArrayList<QuotesModel>>()
    val quotes: LiveData<ArrayList<QuotesModel>> = _quotes

    fun getQuotes() {
        val json = JsonUtils.readJsonFromAsset(app.applicationContext, "quotes.json")
        val gson = Gson()

        val results: Quote = gson.fromJson(json, object : TypeToken<Quote?>() {}.type)

        _quotes.postValue(results.quotes)

    }

}