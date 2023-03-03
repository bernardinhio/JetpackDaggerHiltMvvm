package com.example.templateemptyproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templateemptyproject.datamodel.LotteryData
import com.example.templateemptyproject.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val isBackendLoading = MutableLiveData<Boolean>()
    val lotteriesList = MutableLiveData<Array<LotteryData>?>()
    val backendErrorMessage = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            repository.lotteryMutableStateFlow.collect { backendLotteryResult ->
                when(backendLotteryResult){
                    is BackendLoading -> {
                        isBackendLoading.value = true
                    }
                    is BackendSuccess -> {
                        lotteriesList.value = backendLotteryResult.lotteryData
                    }
                    is BackendFailure -> {
                        backendErrorMessage.value = backendLotteryResult.errorMessage
                    }
                }
            }
        }
    }

    fun fetchApiAndUpdateData(vararg lotteryNames: String) {
        viewModelScope.launch {
            when(lotteryNames.size){
                1 -> repository.fetchListOfLotteries(lotteryNames.get(0))
                2 -> repository.fetchListOfLotteries(lotteryNames.get(0), lotteryNames.get(1))
                3 -> repository.fetchListOfLotteries(lotteryNames.get(0), lotteryNames.get(1), lotteryNames.get(2))
                4 -> repository.fetchListOfLotteries(lotteryNames.get(0), lotteryNames.get(1), lotteryNames.get(2), lotteryNames.get(3))
            }
        }
    }

}