package com.example.templateemptyproject.datamodel

import com.google.gson.annotations.SerializedName

data class Lotto24EurojackpotDrawData(
    @SerializedName("lottery")
    val lotteryName: String? = null,
    val lastDraw: LotteryResults? = null,
    val nextDraw: LotteryResults? = null,
    @SerializedName("draws")
    val otherDraws: Array<LotteryResults>
)