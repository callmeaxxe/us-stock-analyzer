package com.github.callmewaggs.stockanalyzer.controller

import com.github.callmewaggs.stockanalyzer.model.MaxProfit
import com.github.callmewaggs.stockanalyzer.model.StockService
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner::class)
internal class StockControllerTest {

    @MockBean
    private lateinit var stockService: StockService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun viewIndex() {
        // Arrange
        val url = "/"

        // Act
        val actual: ResultActions = mockMvc.perform(get(url))

        // Assert
        actual
                .andExpect(status().isOk)
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("maxProfitForm"))
    }

    @Test
    fun viewIndexWithMaxProfit() {
        // Arrange
        val symbol = "GOOG"
        val url = "/stock/$symbol"
        Mockito.`when`(stockService.getMaxProfit(symbol)).thenReturn(
                MaxProfit(
                        symbol,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        1.0
                )
        )

        // Act
        val actual: ResultActions = mockMvc.perform(get(url))

        // Assert
        actual
                .andExpect(status().isOk)
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("maxProfitForm"))
        verify(stockService).getMaxProfit(symbol)
    }

}