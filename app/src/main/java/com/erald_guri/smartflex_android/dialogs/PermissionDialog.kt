package com.erald_guri.smartflex_android.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.erald_guri.smartflex_android.databinding.PermissionDialogBinding

class PermissionDialog(context: Context) : Dialog(context) {

    private var _binding: PermissionDialogBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        _binding = PermissionDialogBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }

}