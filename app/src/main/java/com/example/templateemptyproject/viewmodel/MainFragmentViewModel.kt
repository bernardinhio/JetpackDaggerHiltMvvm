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
    val listOfLotteries = MutableLiveData<Array<LotteryData>?>()
    val backendErrorMessage = MutableLiveData<String>()
    val isRetryShown = MutableLiveData<Boolean>()

//    fun fetchApiAndUpdateData(firstLottery: String, secondLottery: String) {
    fun fetchApiAndUpdateData(vararg lotteryNames: String) {
        viewModelScope.launch {

//            repository.fetchListOfLotteries(firstLottery, secondLottery)
            when(lotteryNames.size){
                1 -> repository.fetchListOfLotteries(lotteryNames.get(0))
                2 -> repository.fetchListOfLotteries(lotteryNames.get(0), lotteryNames.get(1))
                3 -> repository.fetchListOfLotteries(lotteryNames.get(0), lotteryNames.get(1), lotteryNames.get(2))
                4 -> repository.fetchListOfLotteries(lotteryNames.get(0), lotteryNames.get(1), lotteryNames.get(2), lotteryNames.get(3))
            }

            repository.lotteryMutableStateFlow.collect { backendLotteryResult ->
                when(backendLotteryResult){
                    is BackendLoading -> isBackendLoading.value = true
                    is BackendSuccess -> {
                        isBackendLoading.value = false
                        isRetryShown.value = false
                        backendErrorMessage.value = ""
                        listOfLotteries.value = backendLotteryResult.lotteryData
                    }
                    is BackendFailure -> {
                        isBackendLoading.value = false
                        isRetryShown.value = true
                        backendErrorMessage.value = backendLotteryResult.errorMessage
                        listOfLotteries.value = emptyArray()
                    }
                }
            }
        }
    }

}