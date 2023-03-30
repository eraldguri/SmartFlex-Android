package com.erald_guri.smartflex_android.ui.settings

import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.adapters.SettingsAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.SettingsModel
import com.erald_guri.smartflex_android.databinding.FragmentSettingsBinding
import com.erald_guri.smartflex_android.databinding.LayoutLanguageDialogBinding
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener
import com.erald_guri.smartflex_android.view_models.SettingsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val settings: ArrayList<SettingsModel> = ArrayList()
        val categories = SettingsModel("Category", R.drawable.ic_baseline_category_24, "#f8bf7c")
        val tags = SettingsModel("Tags", R.drawable.ic_baseline_tag_24, "#6589e5")
        val language = SettingsModel("Language", R.drawable.ic_baseline_language_24, "#e56470")
        val contacts = SettingsModel("Accounts", R.drawable.ic_baseline_manage_accounts_24, "#3f5061")

        settings.add(categories)
        settings.add(tags)
        settings.add(language)
        settings.add(contacts)

        val settingsAdapter = SettingsAdapter(settings, onItemClickListener)
        binding.includeRecycler.recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 4)
            adapter = settingsAdapter
        }
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {

    }

    override fun onToolbar(toolbar: Toolbar?) {

    }

    private val onItemClickListener = object : OnItemClickListener<SettingsModel> {
        override fun onItemClick(position: Int, item: SettingsModel) {
            when (position) {
                0 -> {
                    val action = SettingsFragmentDirections.actionNavSettingsToCategoryFragment()
                    findNavController().navigate(action)
                }
                1 -> {
                    //TODO: tags
                }
                2 -> { languageDialog() }
                3 -> {
//                    val action = SettingsFragmentDirections.actionNavSettingsToAccountsFragment()
//                    findNavController().navigate(action)
                }
            }
        }

    }

    private fun languageDialog() {
        val languageBottomSheetDialog = BottomSheetDialog(requireContext())
        val languageDialogBinding = LayoutLanguageDialogBinding.inflate(layoutInflater)
        languageBottomSheetDialog.setContentView(languageDialogBinding.root)

        var language = ""
        languageDialogBinding.btnEnglish.setOnClickListener {
            language = "en"
        }

        languageDialogBinding.btnChinese.setOnClickListener {
            language = "zh-rCN"
        }

        languageDialogBinding.btnSave.setOnClickListener {
            viewModel.setLanguage(language)
            requireActivity().recreate()
        }

        languageBottomSheetDialog.show()
    }
}