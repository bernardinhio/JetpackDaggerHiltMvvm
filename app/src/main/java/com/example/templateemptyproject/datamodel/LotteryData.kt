package com.example.templateemptyproject.datamodel

import com.google.gson.annotations.SerializedName

data class LotteryData(
    @SerializedName("lottery")
    val name: String? = null,
    val lastDraw: LotteryResults? = null,
    val nextDraw: LotteryResults? = null,
    @SerializedName("draws")
    val otherDraws: Array<LotteryResults?>? = null
)