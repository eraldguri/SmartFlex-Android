package com.erald_guri.smartflex_android.ui.contacts.view.tabs.activities.pages

import androidx.appcompat.widget.Toolbar
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentAllActivitiesFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AllActivitiesFragmentFragment : BaseFragment<FragmentAllActivitiesFragmentBinding>(
    FragmentAllActivitiesFragmentBinding::inflate
) {

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

    override fun onToolbar(toolbar: Toolbar?) {

    }

}