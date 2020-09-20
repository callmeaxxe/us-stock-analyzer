package com.github.callmewaggs.stockanalyzer.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class StockController {
    @GetMapping("/")
    fun getIndexView(model: Model): String {
        val maxProfit = null
        model.addAttribute("maxProfit", maxProfit)
        return "index"
    }

    @GetMapping("/stock/{symbol}")
    fun getMaxProfit(@PathVariable symbol: String, model: Model): String {
        model.addAttribute("symbol", symbol)
        model.addAttribute("maxProfit", 10)
        return "index"
    }
}