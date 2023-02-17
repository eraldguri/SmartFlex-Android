package com.erald_guri.smartflex_android.utils

import android.content.Context
import java.io.IOException

object JsonUtils {

    @Throws(IOException::class)
    fun readJsonFromAsset(context: Context, fileName: String): String {
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    }

}