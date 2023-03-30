package com.erald_guri.smartflex_android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.erald_guri.smartflex_android.MainActivity
import com.erald_guri.smartflex_android.R
import com.erald_guri.smartflex_android.interfaces.OnActivityActionsListener
import com.google.android.material.floatingactionbutton.FloatingActionButton

abstract class BaseFragment<VB: ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : Fragment(), OnActivityActionsListener {

    private var _binding: VB? = null
    val binding get() = _binding as VB

    private var _fabButton: FloatingActionButton? = null
    val fabButton get() = _fabButton

    private var _toolbar: Toolbar? = null
    val toolbar get() = _toolbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(inflater)
        if (_binding == null) {
            throw IllegalArgumentException("Binding cannot be null")
        }

        _fabButton = (requireActivity() as MainActivity).findViewById(R.id.fab)
        this.onFabButton(_fabButton)

        _toolbar = (requireActivity() as MainActivity).findViewById(R.id.toolbar)
        this.onToolbar(_toolbar)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}