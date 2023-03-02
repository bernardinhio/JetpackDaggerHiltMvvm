package com.example.templateemptyproject.api

import com.example.templateemptyproject.datamodel.LotteryData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApi {

    companion object{
        // ex: https://www.lotto24.de/public/v1/drawinfo/aggregated/6aus49,eurojackpot
        const val BASE_URL = "https://www.lotto24.de/"
        const val END_POINT = "public/v1/drawinfo/aggregated"
    }

    // Ex: https://www.lotto24.de/public/v1/drawinfo/aggregated/6aus49,eurojackpot
    @GET("$END_POINT/{lottery_name_1},{lottery_name_2}")
    suspend fun getLotteryDataFromGames(
        @Path(value = "lottery_name_1", encoded = true) firstLotteryName: String,
        @Path(value = "lottery_name_2", encoded = true) secondLotteryName: String
    ): Response<Array<LotteryData>>



    // https://www.lotto24.de/public/v1/drawinfo/aggregated/6aus49,eurojackpot,spiel77
    @GET("$END_POINT/{lottery_name_1},{lottery_name_2},{lottery_name_3}")
    suspend fun getLotteryDataFromGames(
        @Path(value = "lottery_name_1", encoded = true) firstLotteryName: String,
        @Path(value = "lottery_name_2", encoded = true) secondLotteryName: String,
        @Path(value = "lottery_name_3", encoded = true) thirdLotteryName: String
    ): Response<Array<LotteryData>>

    // https://www.lotto24.de/public/v1/drawinfo/aggregated/6aus49
    @GET("$END_POINT/{lottery_name_1}")
    suspend fun getLotteryDataFromGames(
        @Path(value = "lottery_name_1", encoded = true) firstLotteryName: String
    ): Response<Array<LotteryData>>

    // https://www.lotto24.de/public/v1/drawinfo/aggregated/6aus49,eurojackpot,spiel77,super6
    @GET("$END_POINT/{lottery_name_1},{lottery_name_2},{lottery_name_3},{lottery_name_4}")
    suspend fun getLotteryDataFromGames(
        @Path(value = "lottery_name_1", encoded = true) firstLotteryName: String,
        @Path(value = "lottery_name_2", encoded = true) secondLotteryName: String,
        @Path(value = "lottery_name_3", encoded = true) thirdLotteryName: String,
        @Path(value = "lottery_name_4", encoded = true) fourthLotteryName: String
    ): Response<Array<LotteryData>>

}