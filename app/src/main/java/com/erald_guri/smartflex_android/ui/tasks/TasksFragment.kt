package com.erald_guri.smartflex_android.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.erald_guri.smartflex_android.BaseFragment
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.adapters.ButtonAdapter
import com.erald_guri.smartflex_android.data.model.ButtonModel
import com.erald_guri.smartflex_android.databinding.FragmentTasksBinding
import com.erald_guri.smartflex_android.interfaces.OnItemClickListener
import com.erald_guri.smartflex_android.interfaces.OnRecyclerItemClickListener
import com.erald_guri.smartflex_android.utils.GridLayoutDecoration

class TasksFragment : BaseFragment<FragmentTasksBinding>(
    FragmentTasksBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drawables = arrayOf(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_school_24)?.mutate(),
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_sports_football_24)?.mutate(),
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_edit_24)?.mutate(),
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_dry_24)?.mutate()
        )


        val buttons = ArrayList<ButtonModel>()
        val buttonEducation = ButtonModel(1, "Education", drawables[0], "#dcf4f5")
        val buttonSport = ButtonModel(2, "Sport", drawables[1], "#feeae6")
        val buttonMeeting = ButtonModel(3, "Meeting", drawables[2], "#fcf1dd")
        val buttonFriends = ButtonModel(4, "Friends", drawables[3], "#ceecfe")

        buttons.add(buttonEducation)
        buttons.add(buttonSport)
        buttons.add(buttonMeeting)
        buttons.add(buttonFriends)

        val buttonAdapter = ButtonAdapter(buttons)
        buttonAdapter.setOnItemClickListener(onRecyclerItemClickListener)
        binding.includeRecyclerCategory.recycler.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
//            addItemDecoration(GridLayoutDecoration(3, 3, true))
            adapter = buttonAdapter
        }
    }

    private val onRecyclerItemClickListener = object : OnItemClickListener {
        override fun onItemClick(position: Int) {

        }


    }

}