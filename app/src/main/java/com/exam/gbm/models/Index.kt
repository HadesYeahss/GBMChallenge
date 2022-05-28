package com.exam.gbm.models

/**
 * Index model to service response
 *
 * @author Rigoberto Torres on 22/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
data class Index(
    val date: String,
    val price: Double,
    val percentageChange: Double,
    val volume: Int,
    val change: Double
)