package com.example.templateemptyproject.repository

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

    suspend fun fetchListOfLotteries(vararg lotteryNames: String){

        lotteryMutableStateFlow.value = BackendLoading
        delay(5_000)  // Simulate Late for UI

        try {

            val response: Response<Array<LotteryData>>? = when(lotteryNames.size){
                1 -> retrofitApi.getLotteryDataFromGames(lotteryNames.get(0))
                2 -> retrofitApi.getLotteryDataFromGames(lotteryNames.get(0), lotteryNames.get(1))
                3 -> retrofitApi.getLotteryDataFromGames(lotteryNames.get(0), lotteryNames.get(1), lotteryNames.get(2))
                4 -> retrofitApi.getLotteryDataFromGames(lotteryNames.get(0), lotteryNames.get(1), lotteryNames.get(2), lotteryNames.get(3))
                else -> null
            }

            if (response?.code() == HttpURLConnection.HTTP_OK) {
                val responseBody: Array<LotteryData>? = response.body()

                if (!responseBody.isNullOrEmpty()){
                    lotteryMutableStateFlow.value = BackendSuccess(responseBody)

                } else{
                    lotteryMutableStateFlow.value = BackendFailure("No Lottery Data now!")
                }
            }
        } catch (e: IOException) {
            lotteryMutableStateFlow.value = BackendFailure("Failure: No Internet!")
        } catch (exception: HttpException) {
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