package com.example.templateemptyproject.datamodel

data class LotteryResults(
    // Both NextDraw and LastDraw
    val drawIdentifier: String? = null, // Both: ex: 2023-02-25
    val lottery: String? = null, // Both: ex: 6aus49 and eurojackpot
    val drawDate: String? = null, // Both: ex: 2023-02-24T20:00:00+02:00
    val drawDateUtc: String? = null, // Both: ex: 2023-02-24T18:00:00Z"

    // Only NextDraw
    val timeZone: String? = null,  // Only NextDraw: ex: Europe/Berlin : Timezone of the draw that can be drawn worldwide
    val cutofftime: String? = null, // Only NextDraw: Ex: 2023-03-01T17:45:00+01:00
    val jackpot: Jackpot? = null, // Only NextDraw: Details

    // Only LastDraw
    val drawResult: DrawResult? = null, // the results numbers for Lott24 and EuroJackpot
    val quotas: Quotas? = null,
    val nonMonetaryQuotas: Quotas? = null,
    val winners: Winners? = null,
    val totalStake: String? = null, // The sum of all winnings
    val currency: String? = null
)