package com.github.callmewaggs.stockanalyzer

import com.github.callmewaggs.stockanalyzer.model.yahoo.YahooStockFetcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate

internal class YahooStockFetcherTest {

    private var yahooStockFetcher: YahooStockFetcher = YahooStockFetcher(RestTemplate())

    @Test
    fun getHistoricalData_success() {
        // Arrange
        val validSymbol = "GOOG"

        // Act
        val actual = yahooStockFetcher.getHistoricalData(validSymbol)

        // Assert
        assertEquals(180, actual.size)
    }

    @Test
    fun getHistoricalData_non_exist_symbol() {
        // Arrange
        val nonExistSymbol = "nonexistsymbol"
        // Act
        val actual = yahooStockFetcher.getHistoricalData(nonExistSymbol)

        // Assert
        assertNotNull(actual)
        assertEquals(0, actual.size)
    }
}