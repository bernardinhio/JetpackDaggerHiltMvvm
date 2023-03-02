package com.example.templateemptyproject.datamodel

import com.example.templateemptyproject.datamodel.Jackpots
import com.google.gson.annotations.SerializedName

data class Jackpot(
    val jackpotSupported: Boolean? = null,
    @SerializedName("jackpots")
    val allJackpots: Jackpots? = null,
    val currency: String? = null,
    val drawIdentifier: String? = null, // not used
    val lottery: String? = null, // not used
    val drawDate: String? = null // not used
)