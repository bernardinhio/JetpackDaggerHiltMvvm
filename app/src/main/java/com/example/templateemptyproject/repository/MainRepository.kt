package com.example.templateemptyproject.repository

import android.util.Log
import com.example.templateemptyproject.api.RetrofitApi
import com.example.templateemptyproject.datamodel.LotteryData
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

//    suspend fun fetchListOfLotteries(firstLottery: String, secondLottery: String){
    suspend fun fetchListOfLotteries(vararg lotteryNames: String){
        try {
//            val response: Response<Array<LotteryData>>? = retrofitApi.getLotteryDataFromGames(firstLottery, secondLottery)

            val response: Response<Array<LotteryData>>? = when(lotteryNames.size){
                1 -> retrofitApi.getLotteryDataFromGames(lotteryNames.get(0))
                2 -> retrofitApi.getLotteryDataFromGames(lotteryNames.get(0), lotteryNames.get(1))
                3 -> retrofitApi.getLotteryDataFromGames(lotteryNames.get(0), lotteryNames.get(1), lotteryNames.get(2))
                4 -> retrofitApi.getLotteryDataFromGames(lotteryNames.get(0), lotteryNames.get(1), lotteryNames.get(2), lotteryNames.get(3))
                else -> null
            }

            // Simulate Late
            lotteryMutableStateFlow.value = BackendLoading
            delay(5_000)

            if (response?.code() == HttpURLConnection.HTTP_OK) {
                val responseBody: Array<LotteryData>? = response.body()

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
            lotteryMutableStateFlow.value = BackendFailure("Failure: No Internet!")

        } catch (exception: HttpException) {
            Log.d("retrofitCall", "Server Broken!")
            lotteryMutableStateFlow.value = BackendFailure("Failure: Server Broken!")
        }

    }
}

// Backend Status
sealed class BackendLotteryResult
object BackendNotCalledYet: BackendLotteryResult()
object BackendLoading: BackendLotteryResult()
data class BackendSuccess(val lotteryData: Array<LotteryData>?): BackendLotteryResult()
data class BackendFailure(val errorMessage: String): BackendLotteryResult()