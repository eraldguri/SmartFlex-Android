package com.erald_guri.smartflex_android.ui.contacts

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentContactViewBinding
import com.erald_guri.smartflex_android.view_models.ContactViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

                tableValues(it.email,           tvEmail,                    getString(R.string.email))
                tableValues(it.phone,           tvPhone,                    getString(R.string.phone))
                tableValues(it.title,           tvTitleValue,               getString(R.string.title))
                tableValues(it.accountName,     tvAccountNameValue,         getString(R.string.account_name))
                tableValues(it.company,         tvCompanyValue,             getString(R.string.company))
                tableValues(it.vendorName,      tvVendorNameValue,          getString(R.string.vendor_name))
                tableValues(it.leadSource,      tvVendorLeadSource,         getString(R.string.lead_source))
                tableValues(it.dateOfBirth,     tvBirthdayValue,            getString(R.string.date_of_birth))
                tableValues(it.phone,           tvPhoneValue,               getString(R.string.phone))
                tableValues(it.otherPhone,      tvOtherPhoneValue,          getString(R.string.other_phone))
                tableValues(it.mobile,          tvMobileValue,              getString(R.string.mobile))
                tableValues(it.secondaryEmail,  tvSecondaryEmailValue,      getString(R.string.secondary_email))
                tableValues(it.street,          tvStreetValue,              getString(R.string.street))
                tableValues(it.city,            tvCityValue,                getString(R.string.city))
                tableValues(it.zipCode,         tvZipCodeValue,             getString(R.string.zip_code))
                tableValues(it.otherZipCode,    tvOtherZipCodeValue,        getString(R.string.other_zip_code))
                tableValues(it.state,           tvStateValue,               getString(R.string.state))
                tableValues(it.otherStreet,     tvOtherStreetValue,         getString(R.string.other_street))
                tableValues(it.otherCity,       tvOtherCityValue,           getString(R.string.other_city))
                tableValues(it.otherState,      tvOtherStateValue,          getString(R.string.other_state))
                tableValues(it.otherCountry,    tvOtherCountryValue,        getString(R.string.other_country))
            }
        }
    }

    private fun tableValues(item: String, value: TextView, field: String) {
        val italicTypeface = Typeface.defaultFromStyle(Typeface.ITALIC)
        if (item.isNotEmpty()) {
            value.text = item
        } else {
            value.typeface = italicTypeface
            value.text = field + " " + getString(R.string.is_not_set)
        }
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

    override fun onToolbar(toolbar: Toolbar?) {
        toolbar?.title = "Profile"
        val menu = toolbar?.menu
        val editMenuItem = menu?.findItem(R.id.action_edit)
        editMenuItem?.setOnMenuItemClickListener {
            val action = ContactViewFragmentDirections.actionContactViewFragmentToAddContactFragment(true, contactId)
            findNavController().navigate(action)
            false
        }
    }

}