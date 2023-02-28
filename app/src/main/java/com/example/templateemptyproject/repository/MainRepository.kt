package com.example.templateemptyproject.repository

import android.util.Log
import com.example.templateemptyproject.api.RetrofitApi
import com.example.templateemptyproject.datamodel.Lotto24EurojackpotDrawData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val retrofitApi: RetrofitApi
) {
    val lotteryMutableStateFlow = MutableStateFlow<BackendLotteryResult>(BackendNotCalledYet)

    suspend fun fetchApiAndUpdateData(){
        try {
            val response: Response<Array<Lotto24EurojackpotDrawData>> = retrofitApi.getLotto24EurojackpotDrawData()

            // Simulate Late
            lotteryMutableStateFlow.value = BackendLoading
            delay(5_000)

            if (response.code() == HttpURLConnection.HTTP_OK) {
                val responseBody: Array<Lotto24EurojackpotDrawData>? = response.body()

                if (!responseBody.isNullOrEmpty()){
                    lotteryMutableStateFlow.value = BackendSuccess(responseBody)
                    Log.d("retrofitCall", responseBody.toString())

                } else{
                    lotteryMutableStateFlow.value = BackendFailure("No Lottery Data now!")
                    Log.d("retrofitCall", "No Lottery Data now!")
                }
            }

        } catch (e: IOException) {
            Log.d("retrofitCall", "No Internet!")
            lotteryMutableStateFlow.value = BackendFailure("No Internet!")

        } catch (exception: HttpException) {
            Log.d("retrofitCall", "Server Broken!")
            lotteryMutableStateFlow.value = BackendFailure("Server Broken!")
        }

    }
}

// Backend Status
sealed class BackendLotteryResult
object BackendNotCalledYet: BackendLotteryResult()
object BackendLoading: BackendLotteryResult()
data class BackendSuccess(val lotteryData: Array<Lotto24EurojackpotDrawData>?): BackendLotteryResult()
data class BackendFailure(val errorMessage: String): BackendLotteryResult()