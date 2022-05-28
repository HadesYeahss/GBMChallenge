package com.exam.gbm.utils

import com.exam.gbm.models.Index
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


/**
 * Functions for get chart name
 *
 * @author Rigoberto Torres on 23/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
class DateFormatChart(private val indicator: List<Index>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return value.toString()
    }

    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        val index = getNextValueNumber(value)
        if (index >= 0 && index < indicator.size) {
            val dateLong = convertDateToLong(indicator[index].date)
            val date = convertLongToDate(dateLong)
            val calendar = Calendar.getInstance()
            calendar.time = date
            return "${calendar.get(Calendar.HOUR)}:${calendar.get(Calendar.MINUTE)}"
        }
        return ""
    }

    fun getNextValueNumber(value: Float): Int {
        if (value >= 0) {
            val aux = value.roundToInt()
            return if (aux >= value)
                aux
            else
                (aux + 1)
        }
        return -1
    }

    fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return df.parse(date).time
    }

    fun convertLongToDate(time: Long): Date {
        val date = Date(time)
        return date
    }
}