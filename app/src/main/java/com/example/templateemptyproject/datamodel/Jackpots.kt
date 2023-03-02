package com.example.templateemptyproject.datamodel

import com.google.gson.annotations.SerializedName

data class Jackpots(
    @SerializedName("WC_1")
    val jackpotOne: String? = null,
    @SerializedName("WC_2")
    val jackpotTwo: String? = null
)