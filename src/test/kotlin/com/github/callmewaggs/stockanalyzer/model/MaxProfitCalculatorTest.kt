package com.github.callmewaggs.stockanalyzer.model

import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class MaxProfitCalculatorTest {
    private val maxProfitCalculator = MaxProfitCalculator()

    @Test
    fun `lowest stock 이 왼쪽이고, highest stock 이 오른쪽인 normal case`() {
        // Arrange
        val symbol = "GOOG"
        val historicalData: MutableList<Stock> = ArrayList()

        val stock1 = Stock(LocalDateTime.now().minusDays(2), 3.0, 1.0)
        val stock2 = Stock(LocalDateTime.now().minusDays(1), 2.0, 2.0)
        val stock3 = Stock(LocalDateTime.now().minusDays(0), 4.0, 3.0)

        historicalData.add(stock1)
        historicalData.add(stock2)
        historicalData.add(stock3)

        // Act
        val actual = maxProfitCalculator.calculate(symbol, historicalData)

        // Assert
        assertEquals(stock1.date, actual.buyDate)
        assertEquals(stock3.date, actual.sellDate)
        assertEquals(stock3.high - stock1.low, actual.profit)
    }

    @Test
    fun `highest stock 이 왼쪽이고, lowest stock 이 오른쪽인 edge case - profitA 가 더 큰 경우`() {
        // Arrange
        val symbol = "GOOG"
        val historicalData: MutableList<Stock> = ArrayList()

        val stock1 = Stock(LocalDateTime.now().minusDays(9), 2.0, 1.0)
        val stock2 = Stock(LocalDateTime.now().minusDays(8), 5.0, 2.0)
        val stock3 = Stock(LocalDateTime.now().minusDays(7), 10.0, 7.0)
        val stock4 = Stock(LocalDateTime.now().minusDays(6), 5.0, 1.0)
        val stock5 = Stock(LocalDateTime.now().minusDays(5), 2.0, 0.0)
        val stock6 = Stock(LocalDateTime.now().minusDays(4), 4.0, 1.0)
        val stock7 = Stock(LocalDateTime.now().minusDays(3), 6.0, 2.0)
        val stock8 = Stock(LocalDateTime.now().minusDays(2), 8.0, 5.0)
        val stock9 = Stock(LocalDateTime.now().minusDays(1), 7.0, 6.0)
        val stock10 = Stock(LocalDateTime.now().minusDays(0), 6.0, 5.0)

        historicalData.add(stock1)
        historicalData.add(stock2)
        historicalData.add(stock3)
        historicalData.add(stock4)
        historicalData.add(stock5)
        historicalData.add(stock6)
        historicalData.add(stock7)
        historicalData.add(stock8)
        historicalData.add(stock9)
        historicalData.add(stock10)

        // Act
        val actual = maxProfitCalculator.calculate(symbol, historicalData)

        // Assert
        assertEquals(stock1.date, actual.buyDate)
        assertEquals(stock3.date, actual.sellDate)
        assertEquals(stock3.high - stock1.low, actual.profit)
    }

    @Test
    fun `highest stock 이 왼쪽이고, lowest stock 이 오른쪽인 edge case - profitB 가 더 큰 경우`() {
        // Arrange
        val symbol = "GOOG"
        val historicalData: MutableList<Stock> = ArrayList()

        val stock1 = Stock(LocalDateTime.now().minusDays(9), 4.0, 3.0)
        val stock2 = Stock(LocalDateTime.now().minusDays(8), 5.0, 2.0)
        val stock3 = Stock(LocalDateTime.now().minusDays(7), 10.0, 7.0)
        val stock4 = Stock(LocalDateTime.now().minusDays(6), 5.0, 1.0)
        val stock5 = Stock(LocalDateTime.now().minusDays(5), 2.0, 0.0)
        val stock6 = Stock(LocalDateTime.now().minusDays(4), 4.0, 1.0)
        val stock7 = Stock(LocalDateTime.now().minusDays(3), 6.0, 2.0)
        val stock8 = Stock(LocalDateTime.now().minusDays(2), 9.0, 5.0)
        val stock9 = Stock(LocalDateTime.now().minusDays(1), 7.0, 6.0)
        val stock10 = Stock(LocalDateTime.now().minusDays(0), 6.0, 5.0)

        historicalData.add(stock1)
        historicalData.add(stock2)
        historicalData.add(stock3)
        historicalData.add(stock4)
        historicalData.add(stock5)
        historicalData.add(stock6)
        historicalData.add(stock7)
        historicalData.add(stock8)
        historicalData.add(stock9)
        historicalData.add(stock10)

        // Act
        val actual = maxProfitCalculator.calculate(symbol, historicalData)

        // Assert
        assertEquals(stock5.date, actual.buyDate)
        assertEquals(stock8.date, actual.sellDate)
        assertEquals(stock8.high - stock5.low, actual.profit)
    }
}