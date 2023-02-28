package com.example.templateemptyproject.api

import com.example.templateemptyproject.datamodel.Lotto24EurojackpotDrawData
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitApi {

    companion object{

        const val BASE_URL = "https://www.lotto24.de/public/v1/drawinfo/aggregated/"
        const val END_POINT = "6aus49,eurojackpot"
    }

    @GET(END_POINT)
    suspend fun getLotto24EurojackpotDrawData(): Response<Array<Lotto24EurojackpotDrawData>>

}