package com.erald_guri.smartflex_android.ui.contacts.add_contact

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.erald_guri.smartflex_android.ui.contacts.add_contact.pages.AddressInfoFragment
import com.erald_guri.smartflex_android.ui.contacts.add_contact.pages.ContactInfoFragment
import com.erald_guri.smartflex_android.ui.contacts.add_contact.pages.DescriptionInfoFragment

const val NUM_PAGES = 3

class AddContactPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ContactInfoFragment()
            1 -> AddressInfoFragment()
            else -> DescriptionInfoFragment()
        }
    }
}