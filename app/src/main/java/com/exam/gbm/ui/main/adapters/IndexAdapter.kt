package com.exam.gbm.ui.main.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.exam.gbm.R
import com.exam.gbm.databinding.SingleItemBinding
import com.exam.gbm.models.IndexComplex
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Adapter for the list of elements
 *
 * @author Rigoberto Torres on 28/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
class IndexAdapter(private var dataSet: List<IndexComplex>) :
    RecyclerView.Adapter<IndexAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(val binding: SingleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val current = dataSet[position]
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.DOWN
            val lastPrice = df.format(current.lastPrice)
            val percentageChange = df.format(current.percentageChange)
            binding.tvIssueId.text = current.issueId
            binding.tvLastPrice.text = "$${lastPrice}"
            binding.tvPercentageChange.text = "${percentageChange}%"
            binding.tvPercentageChange.setTextColor(
                when (current.riseLowTypeId) {
                    2 -> Color.GREEN
                    1 -> Color.RED
                    else -> Color.GREEN
                }
            )
        }
    }

    override fun getItemCount() = dataSet.size

}
