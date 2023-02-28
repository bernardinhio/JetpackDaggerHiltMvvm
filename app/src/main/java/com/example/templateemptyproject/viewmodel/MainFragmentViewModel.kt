package com.example.templateemptyproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.templateemptyproject.datamodel.Lotto24EurojackpotDrawData
import com.example.templateemptyproject.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val lotteryMutableLiveData = MutableLiveData<Lotto24EurojackpotDrawData>()

    fun fetchApiAndUpdateData() {
        viewModelScope.launch {
            repository.lotteryMutableStateFlow.collect { backendLotteryResult ->
                when(backendLotteryResult){
                    is BackendNotCalledYet -> { }
                    is BackendLoading -> { }
                    is BackendFailure -> { }
                    is BackendSuccess -> { }
                }
            }
            repository.fetchApiAndUpdateData()
        }
    }

}