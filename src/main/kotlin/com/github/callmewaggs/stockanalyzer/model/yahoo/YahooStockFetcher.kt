package com.github.callmewaggs.stockanalyzer.model.yahoo

import com.github.callmewaggs.stockanalyzer.model.Stock
import com.github.callmewaggs.stockanalyzer.model.StockFetcher
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.Instant
import java.time.ZoneId

@Service
class YahooStockFetcher(private val restTemplate: RestTemplate) : StockFetcher {

    override fun getHistoricalData(symbol: String): List<Stock> {
        val response = getResponse(symbol)
        if (response.body == null) {
            return emptyList()
        }
        val historicalData: MutableList<Stock> = ArrayList()
        val prices = response.body!!.prices
        prices.take(180).forEach { price ->
            historicalData.add(
                    Stock(
                            Instant.ofEpochSecond(price.date.toLong())
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime(),
                            price.high.toDouble(),
                            price.low.toDouble()
                    )
            )
        }
        return historicalData.reversed()
    }

    private fun getResponse(symbol: String): ResponseEntity<YahooHistoricalDataResponse> {
        val url = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v3/get-historical-data?region=US&symbol=$symbol"
        return restTemplate.exchange(url, HttpMethod.GET, HttpEntity<String>(getHeaders()), YahooHistoricalDataResponse::class.java)
    }

    private fun getHeaders(): HttpHeaders {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON;
        httpHeaders.set("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
        httpHeaders.set("x-rapidapi-key", "223e57eaabmsh01888e31746f3d6p1eb2fejsnafc30fd30384")
        return httpHeaders
    }

}