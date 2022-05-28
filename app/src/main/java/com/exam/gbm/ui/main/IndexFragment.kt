package com.exam.gbm.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.exam.gbm.R
import com.exam.gbm.databinding.IndexFragmentBinding
import com.exam.gbm.databinding.IndexListFragmentBinding
import com.exam.gbm.models.Index
import com.exam.gbm.remote.responses.ResponseResult
import com.exam.gbm.ui.main.viewmodel.IpcViewModel
import com.exam.gbm.utils.Constants.Companion.CHART_ANIMATION_SPEED
import com.exam.gbm.utils.Constants.Companion.CHART_AXIS_MINIMUM
import com.exam.gbm.utils.Constants.Companion.CHART_LABEL_COUNT
import com.exam.gbm.utils.Constants.Companion.CHART_SET_FILL_ALPHA
import com.exam.gbm.utils.Constants.Companion.CHART_SET_LINE_WIDTH
import com.exam.gbm.utils.Constants.Companion.CHART_TEXT_SIZE
import com.exam.gbm.utils.DateFormatChart
import com.exam.gbm.utils.MyMarkerView
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment to show chart data
 *
 * @author Rigoberto Torres on 22/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
@AndroidEntryPoint
class IndexFragment : BaseFragment<IndexFragmentBinding>() {

    override fun getViewBinding() = IndexFragmentBinding.inflate(layoutInflater)
    private val viewModel: IpcViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar?.let {
            it.show()
        }
        setHasOptionsMenu(true)
        attachObservers()
    }

    /**
     * Attaches view model [androidx.lifecycle.liveData] observers
     */
    override fun attachObservers() {
        viewModel.getIndex("cc4c350b-1f11-42a0-a1aa-f8593eafeb1e")
        viewModel.indexList.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                fillChart(it)
            }
        }
        viewModel.loader.observe(viewLifecycleOwner) {
            loaderShow(it)
        }
        binding.btnToIndex.setOnClickListener {
            findNavController().navigate(
                IndexFragmentDirections.actionIndexFragmentToIndexOrderFragment()
            )
        }
    }

    /**
     * Set and insert chart in view
     */
    private fun fillChart(data: List<Index>) {
        binding.lineChart.visibility = View.VISIBLE
        val chart = binding.lineChart
        val description = Description()
        description.text = getString(R.string.chart_description)
        chart.description = description
        chart.isDoubleTapToZoomEnabled = false
        chart.setDrawGridBackground(false)
        chart.description.isEnabled = true
        chart.setDrawBorders(true)
        chart.axisRight.setDrawAxisLine(false)
        chart.axisRight.setDrawGridLines(false)
        chart.axisLeft.isEnabled = true
        chart.axisRight.isEnabled = false
        chart.xAxis.setDrawGridLines(false)
        chart.xAxis.setDrawAxisLine(false)
        chart.setTouchEnabled(true)
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(false)
        chart.xAxis.labelCount = CHART_LABEL_COUNT
        chart.xAxis.position = XAxisPosition.BOTTOM
        chart.xAxis.textSize = CHART_TEXT_SIZE
        chart.xAxis.axisMinimum = CHART_AXIS_MINIMUM
        chart.xAxis.valueFormatter = DateFormatChart(data)
        chart.animateX(CHART_ANIMATION_SPEED)
        val values = ArrayList<Entry>()
        var see = 0
        data.forEach {
            values.add(Entry(see.toFloat(), it.price.toFloat()))
            see++
        }
        val set1 = LineDataSet(values, getString(R.string.chart_footer))
        set1.axisDependency = AxisDependency.LEFT
        set1.color = ColorTemplate.getHoloBlue()
        set1.valueTextColor = ColorTemplate.getHoloBlue()
        set1.lineWidth = CHART_SET_LINE_WIDTH
        set1.setDrawCircles(true)
        set1.setDrawValues(true)
        set1.fillAlpha = CHART_SET_FILL_ALPHA
        set1.fillColor = ColorTemplate.getHoloBlue()
        set1.highLightColor = Color.rgb(244, 117, 117)
        set1.setDrawCircleHole(false)
        val dataToShow = LineData(set1)
        dataToShow.setValueTextColor(Color.WHITE)
        dataToShow.setValueTextSize(CHART_TEXT_SIZE)
        val mv = MyMarkerView(requireContext(), R.layout.custom_marker_view)
        mv.chartView = chart
        chart.marker = mv
        chart.data = dataToShow

    }

    private fun loaderShow(flag: Int) {
        binding.progressIndex.visibility = flag
    }


}