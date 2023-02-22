package com.erald_guri.smartflex_android.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.adapters.SettingsAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.SettingsModel
import com.erald_guri.smartflex_android.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settings: ArrayList<SettingsModel> = ArrayList()
        val categories = SettingsModel("Category", R.drawable.ic_baseline_category_24)
        val tags = SettingsModel("Tags", R.drawable.ic_baseline_tag_24)
        val language = SettingsModel("Language", R.drawable.ic_baseline_language_24)
        val contacts = SettingsModel("Contacts", R.drawable.ic_baseline_contacts_24)

        settings.add(categories)
        settings.add(tags)
        settings.add(language)
        settings.add(contacts)

        val settingsAdapter = SettingsAdapter(settings)
        binding.includeRecycler.recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = settingsAdapter
        }
    }
}