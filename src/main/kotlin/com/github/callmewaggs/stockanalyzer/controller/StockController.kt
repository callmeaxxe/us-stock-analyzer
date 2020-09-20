package com.github.callmewaggs.stockanalyzer.controller

import com.github.callmewaggs.stockanalyzer.model.MaxProfitForm
import com.github.callmewaggs.stockanalyzer.model.StockService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class StockController(private val stockService: StockService) {

    @GetMapping("/")
    fun viewIndex(model: Model): String {
        return getIndexView(model, MaxProfitForm())
    }

    @GetMapping("/stock/{symbol}")
    fun viewIndexWithMaxProfit(@PathVariable symbol: String, model: Model): String {
        val maxProfit = stockService.getMaxProfit(symbol)
        val maxProfitForm = MaxProfitForm(
                maxProfit.symbol,
                maxProfit.buyDate.toString(),
                maxProfit.sellDate.toString(),
                maxProfit.profit.toString()
        )
        return getIndexView(model, maxProfitForm)
    }

    private fun getIndexView(model: Model, maxProfitForm: MaxProfitForm): String {
        model.addAttribute("maxProfitForm", maxProfitForm)
        return "index"
    }

}