package com.github.callmewaggs.stockanalyzer.model

import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.time.LocalDateTime

internal class StockServiceTest {

    private val stockFetcher: StockFetcher = mock(StockFetcher::class.java)
    private val maxProfitCalculator: MaxProfitCalculator = mock(MaxProfitCalculator::class.java)
    private val stockService = StockService(stockFetcher, maxProfitCalculator)

    @Test
    fun getMaxProfit() {
        // Arrange
        val symbol = "GOOG"
        val historicalData = emptyList<Stock>()
        val maxProfit = MaxProfit("", LocalDateTime.now(), LocalDateTime.now(), 0.0)

        Mockito.`when`(stockFetcher.getHistoricalData(symbol)).thenReturn(historicalData)
        Mockito.`when`(maxProfitCalculator.calculate(symbol, historicalData)).thenReturn(maxProfit)

        // Act
        val actual = stockService.getMaxProfit(symbol)

        // Assert
        verify(stockFetcher).getHistoricalData(symbol)
        verify(maxProfitCalculator).calculate(symbol, historicalData)
        assertEquals(maxProfit, actual)
    }
}