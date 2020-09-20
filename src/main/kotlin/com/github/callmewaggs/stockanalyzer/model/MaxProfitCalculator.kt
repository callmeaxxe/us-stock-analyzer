package com.github.callmewaggs.stockanalyzer.model

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class MaxProfitCalculator {

    fun calculate(symbol: String, historicalData: List<Stock>): MaxProfit {
        var highestStock = Stock(LocalDateTime.MAX, Double.MIN_VALUE, Double.MIN_VALUE)
        var lowestStock = Stock(LocalDateTime.MIN, Double.MAX_VALUE, Double.MAX_VALUE)
        for (stock in historicalData) {
            if (stock.high >= highestStock.high) {
                highestStock = stock
            }
            if (stock.low < lowestStock.low) {
                lowestStock = stock
            }
        }
        if (highestStock.date.isBefore(lowestStock.date)) {
            var lowestStockBeforeHighest = Stock(LocalDateTime.MIN, Double.MAX_VALUE, Double.MAX_VALUE)
            for (i in 0..historicalData.indexOf(highestStock)) {
                if (historicalData[i].low < lowestStockBeforeHighest.low) {
                    lowestStockBeforeHighest = historicalData[i]
                }
            }
            var highestStockAfterLowest = Stock(LocalDateTime.MAX, Double.MIN_VALUE, Double.MIN_VALUE)
            for (i in historicalData.indexOf(lowestStock) until historicalData.size) {
                if (historicalData[i].high >= highestStockAfterLowest.high) {
                    highestStockAfterLowest = historicalData[i]
                }
            }
            val profitA = highestStock.high - lowestStockBeforeHighest.low
            val profitB = highestStockAfterLowest.high - lowestStock.low
            return if (profitA >= profitB) {
                MaxProfit(symbol, lowestStockBeforeHighest.date, highestStock.date, profitA)
            } else {
                MaxProfit(symbol, lowestStock.date, highestStockAfterLowest.date, profitB)
            }
        } else {
            val profit = highestStock.high - lowestStock.low
            return MaxProfit(symbol, lowestStock.date, highestStock.date, profit)
        }
    }

}