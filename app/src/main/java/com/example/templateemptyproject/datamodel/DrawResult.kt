package com.example.templateemptyproject.datamodel

import com.google.gson.annotations.SerializedName

data class DrawResult(

    // Main nmbers
    @SerializedName("numbers")
    val mainNumbers: Array<Int?>? = null,
    @SerializedName("number")
    val wholeNumber: String? = null,

    // Additional numbers
    @SerializedName("euroNumbers")
    val twoAdditionalEuroJackpotNumers: Array<Int?>? = null,
    @SerializedName("superNumber")
    val oneAdditionalLotto6aus49Number: Int? = null
)