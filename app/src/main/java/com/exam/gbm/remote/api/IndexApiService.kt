package com.exam.gbm.remote.api


import com.exam.gbm.models.Index
import com.exam.gbm.models.IndexComplex
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Rigoberto Torres on 22/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
interface IndexApiService {

    /**
     * Get List of index´s
     * @param index string to search index
     */
    @GET("{id}")
    suspend fun getIndex(@Path("id") index: String): List<Index>

    /**
     * Get List of indexComplex
     * @param index string to search index
     */
    @GET("{id}")
    suspend fun getIndexList(@Path("id") index: String): List<IndexComplex>

}