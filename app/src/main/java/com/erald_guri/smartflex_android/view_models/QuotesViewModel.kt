package com.erald_guri.smartflex_android.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erald_guri.smartflex_android.data.model.CardModel
import com.erald_guri.smartflex_android.utils.JsonUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(val app: Application) : AndroidViewModel(app) {

    private var _quotes = MutableLiveData<ArrayList<CardModel>>()
    val quotes: LiveData<ArrayList<CardModel>> = _quotes

    fun getQuotes() {
        val json = JsonUtils.readJsonFromAsset(app.applicationContext, "quotes.json")
        val jsonObject = JSONObject(json)
        val jsonArray = jsonObject.getJSONArray("quotes")

        var itemCardModel: CardModel?
        val items = ArrayList<CardModel>()
        for (index in 0 until jsonArray.length()) {
            val valueObject = jsonArray.getJSONObject(index)

            val id = valueObject.getInt("id")
            val title = valueObject.getString("title")
            val description = valueObject.getString("description")
            val colorStr = valueObject.getString("color")
            itemCardModel = CardModel(id, title, description, colorStr)
            items.add(itemCardModel)
        }

        _quotes.postValue(items)
    }

}