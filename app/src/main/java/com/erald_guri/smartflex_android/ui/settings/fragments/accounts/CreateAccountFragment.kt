package com.erald_guri.smartflex_android.ui.settings.fragments.accounts

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentCreateAccountBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment : BaseFragment<FragmentCreateAccountBinding>(
    FragmentCreateAccountBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

    override fun onToolbar(toolbar: Toolbar?) {

    }

}