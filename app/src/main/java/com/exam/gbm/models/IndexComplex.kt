package com.exam.gbm.models

/**
 * IndexComplex model to service response
 *
 * @author Rigoberto Torres on 22/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
data class IndexComplex(
    val issueId: String,
    val openPrice: Double,
    val maxPrice: Double,
    val minPrice: Double,
    val percentageChange: Double,
    val valueChange: Double,
    val aggregatedVolume: Double,
    val askPrice: Double,
    val askVolume: Double,
    val ipcParticipationRate: Double,
    val lastPrice: Double,
    val closePrice: Double,
    val riseLowTypeId: Int,
    val instrumentTypeId: Int,
    val benchmarkId: Int,
    val benchmarkPercentage: Double
)