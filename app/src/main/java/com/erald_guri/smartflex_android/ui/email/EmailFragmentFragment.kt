package com.erald_guri.smartflex_android.ui.email

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentEmailBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EmailFragmentFragment : BaseFragment<FragmentEmailBinding>(
    FragmentEmailBinding::inflate
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