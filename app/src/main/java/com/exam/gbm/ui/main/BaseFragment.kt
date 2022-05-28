package com.exam.gbm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Base fragment
 *
 * @author Rigoberto Torres on 22/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
abstract class BaseFragment<T : ViewBinding> : Fragment() {

    protected lateinit var binding: T
    protected abstract fun getViewBinding(): T

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getViewBinding()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        attachObservers()
    }

    open fun setUpViews() {}

    open fun attachObservers() {}

    private fun init() {
        binding = getViewBinding()
    }

}


