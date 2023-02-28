package com.erald_guri.smartflex_android.ui.notes

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.adapters.ColorSelectorAdapter
import com.erald_guri.smartflex_android.adapters.FontSelectorAdapter
import com.erald_guri.smartflex_android.adapters.NoteToolsAdapter
import com.erald_guri.smartflex_android.data.model.FontModel
import com.erald_guri.smartflex_android.data.model.NoteModel
import com.erald_guri.smartflex_android.databinding.ActivityNoteBuilderBinding
import com.erald_guri.smartflex_android.databinding.FontSelectionDialogBinding
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener
import com.erald_guri.smartflex_android.utils.DATE_TIME_FORMAT_GENERAL
import com.erald_guri.smartflex_android.utils.getCurrentDateTime
import com.erald_guri.smartflex_android.utils.toString
import com.erald_guri.smartflex_android.view_models.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
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

        setupRecyclerView()

        binding!!.edNote.addTextChangedListener(onTextChanged)
        val date = getCurrentDateTime().toString(DATE_TIME_FORMAT_GENERAL)
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

    private fun setupRecyclerView() {
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

        val toolsAdapter = NoteToolsAdapter(icons, onItemClickListener)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding!!.recycler.apply {
            layoutManager = linearLayoutManager
            adapter = toolsAdapter
        }
    }

    private val onItemClickListener = object: OnItemClickListener<Int> {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onItemClick(position: Int, item: Int) {
            when (position) {
                0 -> {
                    fontDialog()
                }
            }
        }

    }

    private val onTextChanged = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            Thread {
                try {
                    Thread.sleep(5000)
                    val date = getCurrentDateTime().toString(DATE_TIME_FORMAT_GENERAL)
                    binding!!.tvDateTime.text = date
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }

    }

    private fun createNote() {
        val date = getCurrentDateTime().toString(DATE_TIME_FORMAT_GENERAL)

        val note = NoteModel(binding!!.edTitle.text.toString(), binding!!.edNote.text.toString(), date)
        viewModel.insertNote(note)
        viewModel.success.observe(this) {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun editNote() {
        val date = getCurrentDateTime().toString(DATE_TIME_FORMAT_GENERAL)

        val note = NoteModel(binding!!.edTitle.text.toString(), binding!!.edNote.text.toString(), date)
        note.id = editableNoteId
        viewModel.updateNote(note)
        viewModel.success.observe(this) {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fontDialog() {
        val bottomFontDialog = BottomSheetDialog(this)
        val fontBinding = FontSelectionDialogBinding.inflate(layoutInflater)
        bottomFontDialog.setContentView(fontBinding.root)

        val colors = ArrayList<String>()
        colors.add("#9d683c")
        colors.add("#55dab8")
        colors.add("#e5d2e0")
        colors.add("#ffeaf9")
        colors.add("#f3daff")
        colors.add("#f1cbff")
        colors.add("#009999")
        val colorSelectorAdapter = ColorSelectorAdapter(colors)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        fontBinding.includeRecyclerTextColors.recycler.apply {
            layoutManager = linearLayoutManager
            adapter = colorSelectorAdapter
        }

        val fonts = ArrayList<FontModel>()
        fonts.add(FontModel("Amaranth", ResourcesCompat.getFont(this, R.font.amaranth)))
        fonts.add(FontModel("Averia Gruesa Libre", ResourcesCompat.getFont(this, R.font.averia_gruesa_libre)))
        fonts.add(FontModel("Baloo Tamma", ResourcesCompat.getFont(this, R.font.baloo_tamma)))
        fonts.add(FontModel("Bungee", ResourcesCompat.getFont(this, R.font.bungee)))
        fonts.add(FontModel("Lemon", ResourcesCompat.getFont(this, R.font.lemon)))

        val fontSelectionAdapter = FontSelectorAdapter(fonts)
        fontBinding.includeRecyclerFonts.recycler.apply {
            layoutManager = GridLayoutManager(applicationContext, 3)
            adapter = fontSelectionAdapter
        }

        bottomFontDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}