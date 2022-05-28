package com.exam.gbm.remote.responses

/**
 * @author Rigoberto Torres on 22/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
sealed class ResponseResult<out H> {
    data class Success<out T>(val data: T)
        : ResponseResult<T>()
    data class Failure(val error: Throwable)
        : ResponseResult<Nothing>()
}

