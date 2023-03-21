package com.erald_guri.smartflex_android.utils

import android.text.TextUtils
import android.widget.EditText

fun String.isEmpty(): Boolean {
    return (TextUtils.isEmpty(this)
            || this.equals("", ignoreCase = true)
            || this.equals("{}", ignoreCase = true)
            || this.equals("null", ignoreCase = true)
            || this.equals("undefined", ignoreCase = true)
    )
}

fun EditText.isValidaEmail(): Boolean {
    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    return !this.text.toString().isEmpty() && this.text.toString().matches(emailPattern)
}

fun EditText.isValidEmail(): Boolean {
    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    return !this.text.toString().isEmpty() && this.text.toString().matches(emailPattern)
}

fun String.isValidEmail(): Boolean {
    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
    return !this.isEmpty() && this.matches(emailPattern)
}