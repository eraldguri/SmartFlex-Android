package com.erald_guri.smartflex_android.ui.notes

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.adapters.NoteAdapter
import com.erald_guri.smartflex_android.databinding.ActivityNoteBuilderBinding

class NoteBuilderActivity : AppCompatActivity() {

    private var _binding: ActivityNoteBuilderBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNoteBuilderBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        supportActionBar?.hide()

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
        val toolsAdapter = NoteAdapter(icons)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding!!.recycler.apply {
            layoutManager = linearLayoutManager
            adapter = toolsAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}