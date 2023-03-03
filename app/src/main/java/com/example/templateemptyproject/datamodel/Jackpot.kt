package com.example.templateemptyproject.datamodel

import com.google.gson.annotations.SerializedName

data class Jackpot(
    val jackpotSupported: Boolean? = null,
    @SerializedName("jackpots")
    val allJackpots: Jackpots? = null,
    val currency: String? = null,
    val drawIdentifier: String? = null,
    val lottery: String? = null,
    val drawDate: String? = null
)