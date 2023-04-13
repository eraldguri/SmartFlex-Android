package com.erald_guri.smartflex_android.ui.contacts.view

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.ContactModel
import com.erald_guri.smartflex_android.databinding.FragmentContactViewBinding
import com.erald_guri.smartflex_android.ui.contacts.view.tabs.ContactInfoFragment
import com.erald_guri.smartflex_android.ui.contacts.view.tabs.adapter.ContactPagerAdapter
import com.erald_guri.smartflex_android.view_models.ContactViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactViewFragment : BaseFragment<FragmentContactViewBinding>(
    FragmentContactViewBinding::inflate
) {

    private var contactId: Int = -1

    private val viewModel by viewModels<ContactViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactId = ContactViewFragmentArgs.fromBundle(requireArguments()).contactId
        fetchContact(contactId)

        setupUI()
    }

    private fun setupUI() {
        val tabNames = arrayOf("Activities", "Contact Info", "Accounts", "More")
        val pagerAdapter = ContactPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
        
    }

    @SuppressLint("SetTextI18n")
    private fun fetchContact(contactId: Int) {
        binding.apply {
            viewModel.getContactById(contactId)
            viewModel.contact.observe(viewLifecycleOwner) {
                val imageUri = Uri.parse(it.photoPath)
                photo.imageTintMode = null
                if (it.photoPath.isNotEmpty()) {
                    Glide.with(requireContext())
                        .load("file:" + imageUri)
                        .override(500, 500)
                        .into(photo)
                } else {
                    Glide.with(requireContext())
                        .load(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_emoji_emotions_24))
                        .override(500, 500)
                        .into(photo)
                }
                tvName.text = "${it.firstName} ${it.lastName}"
            }
        }
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

    override fun onToolbar(toolbar: Toolbar?) {
        toolbar?.title = "Profile"
        val menu = toolbar?.menu
//        val editMenuItem = menu?.findItem(R.id.action_edit)
//        editMenuItem?.setOnMenuItemClickListener {
//            val action = ContactViewFragmentDirections.actionContactViewFragmentToAddContactFragment(true, contactId)
//            findNavController().navigate(action)
//            false
//        }
    }

}