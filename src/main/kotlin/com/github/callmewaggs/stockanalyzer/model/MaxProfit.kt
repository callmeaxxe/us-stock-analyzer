package com.github.callmewaggs.stockanalyzer.model

import java.time.LocalDateTime

data class MaxProfit(
        val symbol: String, val buyDate: LocalDateTime, val sellDate: LocalDateTime, val profit: Double
)