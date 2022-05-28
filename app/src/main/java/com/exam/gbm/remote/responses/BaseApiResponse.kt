package com.exam.gbm.remote.responses


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Rigoberto Torres on 22/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
abstract class BaseApiResponse {
    suspend inline fun <T> safeApiCall(
        crossinline body: suspend () -> T
    ): ResponseResult<T> {
        return try {
            val users = withContext(Dispatchers.IO) { body() }
            ResponseResult.Success(users)
        } catch (e: Exception) {
            ResponseResult.Failure(e)
        }
    }
}