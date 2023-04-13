package com.erald_guri.smartflex_android.ui.contacts.view.tabs.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.erald_guri.smartflex_android.data.model.ContactModel
import com.erald_guri.smartflex_android.ui.contacts.view.tabs.ContactAccountsFragment
import com.erald_guri.smartflex_android.ui.contacts.view.tabs.activities.ContactActivitiesFragment
import com.erald_guri.smartflex_android.ui.contacts.view.tabs.ContactInfoFragment
import com.erald_guri.smartflex_android.ui.contacts.view.tabs.ContactMoreFragment

private const val NUM_TABS = 4

class ContactPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ContactActivitiesFragment()
            1 -> ContactInfoFragment()
            2 -> ContactAccountsFragment()
            3 -> ContactMoreFragment()
            else -> ContactActivitiesFragment()
        }
    }
}