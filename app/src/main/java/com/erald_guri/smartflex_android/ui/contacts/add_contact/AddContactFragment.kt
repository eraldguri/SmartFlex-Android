package com.erald_guri.smartflex_android.ui.contacts.add_contact

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.DialogPhotoChoserBinding
import com.erald_guri.smartflex_android.databinding.FragmentAddContactBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactFragment : BaseFragment<FragmentAddContactBinding>(
    FragmentAddContactBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnSelectPhoto.setOnClickListener { photoChooserDialog() }
        }
    }

    private fun photoChooserDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        val photoChooserBinding = DialogPhotoChoserBinding.inflate(layoutInflater)
        alertDialog.setView(photoChooserBinding.root)

        alertDialog.show()
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
        fabButton?.hide()
    }

}