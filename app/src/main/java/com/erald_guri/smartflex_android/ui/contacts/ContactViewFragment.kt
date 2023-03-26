package com.erald_guri.smartflex_android.ui.contacts

import android.os.Bundle
import android.view.View
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentContactViewBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactViewFragment : BaseFragment<FragmentContactViewBinding>(
    FragmentContactViewBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

}