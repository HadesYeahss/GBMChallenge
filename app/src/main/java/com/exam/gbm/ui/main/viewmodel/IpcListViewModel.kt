package com.exam.gbm.ui.main.viewmodel

import androidx.lifecycle.*
import com.exam.gbm.models.IndexComplex
import com.exam.gbm.remote.responses.ResponseResult
import com.exam.gbm.repository.IndexRepository
import com.exam.gbm.utils.Constants.Companion.UPPER
import com.exam.gbm.utils.Constants.Companion.LOWER
import com.exam.gbm.utils.Constants.Companion.VOLUME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

/**
 * ViewModel to provide and get data to [IndexOrderFragment]
 *
 * @author Rigoberto Torres on 23/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
@HiltViewModel
class IpcListViewModel @Inject constructor(
    val indexRepository: IndexRepository
) : ViewModel() {

    var indexListUpper = MutableLiveData<List<IndexComplex>>()
    private var orderList = mutableListOf<IndexComplex>()

    /**
     * Get List of index´s
     * @param index string to search index
     */
    fun getIndexList(topic: String) = viewModelScope.launch {
        indexRepository.getIndexList(topic).collect {
            when (it) {
                is ResponseResult.Success -> {
                    it.data.let { list ->
                        //indexListUpper.postValue(list)
                        orderList.clear()
                        val newList = list.groupBy { item -> item.riseLowTypeId }
                        newList[UPPER]?.let { it1 -> orderList.addAll(it1) }
                        newList[LOWER]?.let { it1 -> orderList.addAll(it1) }
                        newList[VOLUME]?.let { it1 -> orderList.addAll(it1) }
                        indexListUpper.postValue(orderList)
                    }
                }
                is ResponseResult.Failure -> {
                    val error = it.error
                    // some error happened
                }
            }
        }
    }
}