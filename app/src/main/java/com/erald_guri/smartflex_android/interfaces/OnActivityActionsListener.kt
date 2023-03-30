package com.erald_guri.smartflex_android.interfaces

import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

interface OnActivityActionsListener {
    fun onFabButton(fabButton: FloatingActionButton?)

    fun onToolbar(toolbar: Toolbar?)
}