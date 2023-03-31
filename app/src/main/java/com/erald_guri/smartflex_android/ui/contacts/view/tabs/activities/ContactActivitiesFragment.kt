package com.erald_guri.smartflex_android.ui.contacts.view.tabs.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentContactActivitiesBinding
import com.erald_guri.smartflex_android.ui.contacts.view.tabs.activities.pages.ActivityTimelineFragment
import com.erald_guri.smartflex_android.ui.contacts.view.tabs.activities.pages.AllActivitiesFragmentFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactActivitiesFragment : BaseFragment<FragmentContactActivitiesBinding>(
    FragmentContactActivitiesBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnAllActivities.setOnClickListener {
                changeButtonStyle(true, btnAllActivities)
                changeButtonStyle(false, btnActivityTimeline)
                changeFragment(AllActivitiesFragmentFragment())
            }
            btnActivityTimeline.setOnClickListener {
                changeButtonStyle(false, btnAllActivities)
                changeButtonStyle(true, btnActivityTimeline)
                changeFragment(ActivityTimelineFragment())
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_contact_activity_container_view, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun changeButtonStyle(isPressed: Boolean, button: AppCompatButton) {
        if (isPressed) {
            if (button == binding.btnAllActivities) {
                button.setBackgroundResource(R.drawable.bg_blue_left_corners)
            } else {
                button.setBackgroundResource(R.drawable.bg_blue_right_corners)
            }
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        } else {
            if (button == binding.btnActivityTimeline) {
                button.setBackgroundResource(R.drawable.border_right_corner_transparent)
            } else {
                button.setBackgroundResource(R.drawable.border_left_corner_transparent)
            }
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

    override fun onToolbar(toolbar: Toolbar?) {

    }

}