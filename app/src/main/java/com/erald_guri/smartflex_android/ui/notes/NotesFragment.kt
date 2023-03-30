package com.erald_guri.smartflex_android.ui.notes

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.erald_guri.smartflex_android.adapters.NoteAdapter
import com.erald_guri.smartflex_android.base.BaseFragment
import com.erald_guri.smartflex_android.data.model.NoteModel
import com.erald_guri.smartflex_android.databinding.FragmentNotesBinding
import com.erald_guri.smartflex_android.interfaces.OnTaskListener
import com.erald_guri.smartflex_android.view_models.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : BaseFragment<FragmentNotesBinding>(
    FragmentNotesBinding::inflate
) {

    private val viewModel by viewModels<NoteViewModel>()
    private var noteAdapter: NoteAdapter? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreateNote.setOnClickListener {
            val intent = Intent(requireActivity(), NoteBuilderActivity::class.java)
            intent.putExtra("isEditMode", false)
            requireActivity().startActivity(intent)
        }

    }

    private fun observeNotes() {
        viewModel.fetchNotes()
        viewModel.notes.observe(viewLifecycleOwner) {
            val noteList = ArrayList<NoteModel>()
            noteList.addAll(it)
            noteAdapter = NoteAdapter(noteList, onNotesListener)
            binding.includeRecycler.recycler.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = noteAdapter
            }
        }
    }

    private val onNotesListener = object: OnTaskListener<NoteModel> {
        override fun onItemClick(task: NoteModel) {

        }

        override fun onEdit(position: Int, task: NoteModel) {
            val intent = Intent(requireContext(), NoteBuilderActivity::class.java)
            intent.putExtra("id", task.id)
            intent.putExtra("title", task.title)
            intent.putExtra("description", task.description)
            intent.putExtra("isEditMode", true)
            requireActivity().startActivity(intent)
        }

        override fun onDelete(position: Int, task: NoteModel) {
            viewModel.deleteNote(task)
            viewModel.success.observe(viewLifecycleOwner) {
                if (!it.error) {
                    noteAdapter?.removeNote(task)
                    if (noteAdapter?.itemCount == 0) {
                        //TODO: empty view
//                        binding.constraintView.visibility = View.GONE
//                        binding.includeNotFound.root.visibility = View.VISIBLE
                    }
                } else {
                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        observeNotes()
    }

    override fun onFabButton(fabButton: FloatingActionButton?) {
    }

    override fun onToolbar(toolbar: Toolbar?) {

    }

}