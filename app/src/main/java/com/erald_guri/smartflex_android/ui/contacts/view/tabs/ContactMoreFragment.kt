package com.erald_guri.smartflex_android.ui.contacts.view.tabs

import androidx.appcompat.widget.Toolbar
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentContactMoreBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactMoreFragment : BaseFragment<FragmentContactMoreBinding>(
    FragmentContactMoreBinding::inflate
) {

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

    override fun onToolbar(toolbar: Toolbar?) {

    }

}