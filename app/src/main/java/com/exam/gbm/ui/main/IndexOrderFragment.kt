package com.exam.gbm.ui.main

import androidx.fragment.app.viewModels
import com.exam.gbm.R
import com.exam.gbm.databinding.IndexListFragmentBinding
import com.exam.gbm.models.IndexComplex
import com.exam.gbm.ui.main.adapters.IndexAdapter
import com.exam.gbm.ui.main.viewmodel.IpcListViewModel
import com.exam.gbm.utils.RecyclerSectionItemDecoration
import com.exam.gbm.utils.RecyclerSectionItemDecoration.SectionCallback
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment to show order list to indexlist
 *
 * @author Rigoberto Torres on 23/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
@AndroidEntryPoint
class IndexOrderFragment : BaseFragment<IndexListFragmentBinding>() {

    override fun getViewBinding() = IndexListFragmentBinding.inflate(layoutInflater)
    private val viewModel: IpcListViewModel by viewModels()
    private lateinit var indexAdapterUpper: IndexAdapter
    private var orderList = mutableListOf<IndexComplex>()

    /**
     * Initializes class members
     */
    override fun setUpViews() {
        indexAdapterUpper = IndexAdapter(orderList)
        val sectionItemDecoration = RecyclerSectionItemDecoration(
            resources.getDimensionPixelSize(R.dimen.dim_30),
            true,
            getSectionCallback(orderList)
        )
        binding.rvUpper.addItemDecoration(sectionItemDecoration!!)
        binding.rvUpper.adapter = indexAdapterUpper
    }

    /**
     * Attaches view model [androidx.lifecycle.liveData] observers
     */
    override fun attachObservers() {
        viewModel.getIndexList("b4eb963c-4aee-4b60-a378-20cb5b00678f")
        viewModel.indexListUpper.observe(viewLifecycleOwner) {
            orderList.clear()
            orderList.addAll(it)
            indexAdapterUpper.notifyDataSetChanged()

        }
    }

    /**
     * Functions to Callback in sectionItemDecorator
     */
    private fun getSectionCallback(index: List<IndexComplex>): SectionCallback {
        return object : SectionCallback {
            override fun isSection(position: Int): Boolean {
                return (position == 0 || index[position]
                    .riseLowTypeId != index[position - 1].riseLowTypeId)
            }

            override fun getSectionHeader(position: Int): CharSequence {
                return when (index[position].riseLowTypeId) {
                    1 -> getString(R.string.top_ten_header_lower)
                    2 -> getString(R.string.top_ten_header_upper)
                    3 -> getString(R.string.top_ten_header_change)
                    else -> {}
                }.toString()
            }
        }
    }
}