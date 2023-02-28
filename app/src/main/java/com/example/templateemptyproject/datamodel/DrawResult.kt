package com.example.templateemptyproject.datamodel

import com.google.gson.annotations.SerializedName

class DrawResult(
    @SerializedName("numbers")
    val mainNumbers: Array<Int?>,
    @SerializedName("euroNumbers")
    val twoAdditionalEuroJackpotNumers: Array<Int?>,
    @SerializedName("superNumber")
    val oneAdditionalLotto24Numer: Int? = null
)