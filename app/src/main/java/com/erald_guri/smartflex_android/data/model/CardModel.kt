package com.erald_guri.smartflex_android.data.model

data class CardModel(
    val id: Int,
    val title: String,
    val description: String,
    val secondDescription: String,
    val color: String
) {
    constructor(
        id: Int,
        title: String,
        description: String,
        color: String
    ) : this(id, title, description, "", color)
}

data class CardItem(
    val id: Int,
    val title: String,
    val color: String
)

