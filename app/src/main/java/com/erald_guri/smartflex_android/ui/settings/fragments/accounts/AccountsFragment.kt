package com.erald_guri.smartflex_android.ui.settings.fragments.accounts

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentAccountsBinding
import com.erald_guri.smartflex_android.view_models.AccountViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountsFragment : BaseFragment<FragmentAccountsBinding>(
    FragmentAccountsBinding::inflate
) {

    private val viewModel by viewModels<AccountViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.show()
        fabButton?.setOnClickListener {
            val action = AccountsFragmentDirections.actionNavAccountsToCreateAccountFragment()
            findNavController().navigate(action)
        }
    }

    override fun onToolbar(toolbar: Toolbar?) {

    }

}