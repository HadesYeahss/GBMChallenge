package com.exam.gbm.ui.main.viewmodel


import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.gbm.models.Index
import com.exam.gbm.remote.responses.ResponseResult
import com.exam.gbm.repository.IndexRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel to provide and get data to [IndexFragment]
 *
 * @author Rigoberto Torres on 22/05/2022.
 * @version 0.0.1
 * @since 0.0.1
 */
@HiltViewModel
class IpcViewModel @Inject constructor(
    val indexRepository: IndexRepository
) : ViewModel() {

    var indexList = MutableLiveData<List<Index>>()
    var loader = MutableLiveData<Int>()

    /**
     * Get List of index´s
     * @param index string to search index
     */
    fun getIndex(topic: String) = viewModelScope.launch {
        loader.postValue(View.VISIBLE)
        indexRepository.getIndex(topic).collect {
            when (it) {
                is ResponseResult.Success -> {
                    loader.postValue(View.GONE)
                    it.data.let { list ->
                        indexList.postValue(list)
                    }
                }
                is ResponseResult.Failure -> {
                    loader.postValue(View.GONE)
                    val error = it.error
                    // some error happened
                }
            }
        }
    }
}