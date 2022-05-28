package com.exam.gbm.repository


import com.exam.gbm.models.Index
import com.exam.gbm.models.IndexComplex
import com.exam.gbm.remote.api.IndexApiService
import com.exam.gbm.remote.responses.BaseApiResponse
import com.exam.gbm.remote.responses.ResponseResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Repository to access api data
 *
 * @author Rigoberto Torres on 22/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
@ActivityRetainedScoped
class IndexRepository @Inject constructor(
    private val indexApiService: IndexApiService
) : BaseApiResponse() {

    /**
     * Get List of index´s
     * @param index string to search index
     */
    suspend fun getIndex(index: String): Flow<ResponseResult<List<Index>>> {
        return flow {
            emit(safeApiCall { indexApiService.getIndex(index) })
        }.flowOn(Dispatchers.IO)

    }

    /**
     * Get List of indexComplex
     * @param index string to search index
     */
    suspend fun getIndexList(index: String): Flow<ResponseResult<List<IndexComplex>>> {
        return flow {
            delay(Duration.ZERO)
            while (true) {
                emit(safeApiCall { indexApiService.getIndexList(index) })
                delay(10.seconds)
            }
        }.flowOn(Dispatchers.IO)

    }

}
