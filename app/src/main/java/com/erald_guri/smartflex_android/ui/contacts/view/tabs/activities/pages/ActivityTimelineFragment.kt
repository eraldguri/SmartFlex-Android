package com.erald_guri.smartflex_android.ui.contacts.view.tabs.activities.pages

import androidx.appcompat.widget.Toolbar
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentActivityTimelineBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ActivityTimelineFragment : BaseFragment<FragmentActivityTimelineBinding>(
    FragmentActivityTimelineBinding::inflate
) {

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

    override fun onToolbar(toolbar: Toolbar?) {

    }

}