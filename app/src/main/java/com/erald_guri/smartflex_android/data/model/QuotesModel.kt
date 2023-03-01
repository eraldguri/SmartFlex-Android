package com.erald_guri.smartflex_android.data.model

import com.google.gson.annotations.SerializedName

data class Quote(
    @SerializedName("quotes")
    var quotes: ArrayList<QuotesModel>
)

data class QuotesModel(
    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("subtitle")
    var subtitle: String,

    @SerializedName("contents")
    var contents: ArrayList<QuoteContent>,

    @SerializedName("color")
    var color: String
)

data class QuoteContent(
    @SerializedName("no")
    var no: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("descriptions")
    var descriptions: ArrayList<String>
)

