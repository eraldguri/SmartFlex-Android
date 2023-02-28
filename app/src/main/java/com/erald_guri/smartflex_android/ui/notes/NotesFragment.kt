package com.erald_guri.smartflex_android.ui.notes

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.databinding.FragmentNotesBinding

class NotesFragment : BaseFragment<FragmentNotesBinding>(
    FragmentNotesBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreateNote.setOnClickListener {
            val intent = Intent(requireActivity(), NoteBuilderActivity::class.java)
            requireActivity().startActivity(intent)
        }
    }

}