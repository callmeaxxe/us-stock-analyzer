package com.github.callmewaggs.stockanalyzer.model

import org.springframework.stereotype.Service

// Fetcher 와 Calc 를 생성자로 받아와서 Fetcher 의 데이터를 Calc 로 보내 계산을 처리한 값을 돌려주는 역할
@Service
class StockService(private val stockFetcher: StockFetcher, private val maxProfitCalculator: MaxProfitCalculator) {
    fun getMaxProfit(symbol: String): MaxProfit {
        val historicalData = stockFetcher.getHistoricalData(symbol)
        return maxProfitCalculator.calculate(symbol, historicalData)
    }
}