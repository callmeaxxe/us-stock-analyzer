package com.github.callmewaggs.stockanalyzer

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate

internal class ApiAdapterTest {

    private var apiAdapter: ApiAdapter = ApiAdapter(RestTemplate())

    @Test
    fun getHistoricalData_success() {
        // Arrange
        val validSymbol = "GOOG"
        // Act
        val actual = apiAdapter.getHistoricalData(validSymbol)

        // Assert
        assertEquals(HttpStatus.OK, actual.statusCode)
        assertNotNull(actual.headers)
        assertNotNull(actual.body)
    }

    @Test
    fun getHistoricalData_non_exist_symbol() {
        // Arrange
        val nonExistSymbol = "nonexistsymbol"
        // Act
        val actual = apiAdapter.getHistoricalData(nonExistSymbol)

        // Assert
        assertEquals(HttpStatus.OK, actual.statusCode)
        assertNotNull(actual.headers)
        assertNull(actual.body)
    }
}