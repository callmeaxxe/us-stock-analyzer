package com.github.callmewaggs.stockanalyzer.model

// 임의의 데이터 소스로부터 180일 간의 데이터를 가져오는 역할
interface StockFetcher {
    fun getHistoricalData(symbol: String): List<Stock>
}