package com.erald_guri.smartflex_android.ui.notes

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.adapters.NoteToolsAdapter
import com.erald_guri.smartflex_android.data.model.NoteModel
import com.erald_guri.smartflex_android.databinding.ActivityNoteBuilderBinding
import com.erald_guri.smartflex_android.utils.getCurrentDateTime
import com.erald_guri.smartflex_android.utils.toString
import com.erald_guri.smartflex_android.view_models.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteBuilderActivity : AppCompatActivity() {

    private var _binding: ActivityNoteBuilderBinding? = null
    private val binding get() = _binding

    private val viewModel by viewModels<NoteViewModel>()

    private var isEditMode: Boolean? = null
    private var editableNoteId: Int? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNoteBuilderBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        supportActionBar?.hide()

        isEditMode = intent.getBooleanExtra("isEditMode", true)

        val icons = ArrayList<Int>()
        icons.add(R.drawable.ic_baseline_format_color_text_24)
        icons.add(R.drawable.ic_baseline_check_box_24)
        icons.add(R.drawable.ic_baseline_mic_none_24)
        icons.add(R.drawable.ic_baseline_create_24)
        icons.add(R.drawable.ic_baseline_image_24)
        icons.add(R.drawable.ic_baseline_emoji_emotions_24)
        icons.add(R.drawable.ic_baseline_color_lens_24)
        icons.add(R.drawable.ic_baseline_format_list_bulleted_24)
        icons.add(R.drawable.ic_baseline_format_list_numbered_24)

        //Adapter
        val toolsAdapter = NoteToolsAdapter(icons)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding!!.recycler.apply {
            layoutManager = linearLayoutManager
            adapter = toolsAdapter
        }

        binding!!.edNote.addTextChangedListener(onTextChanged)
        binding!!.edNote.onFocusChangeListener = onFocusedChangeListener
        val date = getCurrentDateTime().toString("yyyy/MM/dd HH:mm:ss")
        binding!!.tvDateTime.text = date

        if (isEditMode!!) {
            editableNoteId = intent.getIntExtra("id", 0)
            val editableNoteTitle = intent.getStringExtra("title")
            val editableNoteDescription = intent.getStringExtra("description")
            binding!!.edTitle.setText(editableNoteTitle)
            binding!!.edNote.setText(editableNoteDescription)
        }

        binding!!.btnSave.setOnClickListener {
            if (binding!!.edTitle.text.isNotEmpty() && binding!!.edNote.text.isNotEmpty()) {
                if (isEditMode!!) {
                    editNote()
                } else {
                    createNote()
                }
            } else {
                Snackbar.make(binding!!.root, "Fields cannot be empty", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private val onFocusedChangeListener = object : OnFocusChangeListener {
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            if (hasFocus) {
                //TODO
            } else {
//                val date = getCurrentDateTime().toString("yyyy/MM/dd HH:mm:ss")
//                binding!!.tvDateTime.text = date
            }
        }

    }

    private val onTextChanged = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            Thread(Runnable {
                try {
                    Thread.sleep(5000)
                    val date = getCurrentDateTime().toString("yyyy/MM/dd HH:mm:ss")
                    binding!!.tvDateTime.text = date
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }).start()
        }

    }

    private fun createNote() {
        val date = getCurrentDateTime().toString("yyyy/MM/dd HH:mm")

        val note = NoteModel(binding!!.edTitle.text.toString(), binding!!.edNote.text.toString(), date)
        viewModel.insertNote(note)
        viewModel.success.observe(this) {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun editNote() {
        val date = getCurrentDateTime().toString("yyyy/MM/dd HH:mm")

        val note = NoteModel(binding!!.edTitle.text.toString(), binding!!.edNote.text.toString(), date)
        note.id = editableNoteId
        viewModel.updateNote(note)
        viewModel.success.observe(this) {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}