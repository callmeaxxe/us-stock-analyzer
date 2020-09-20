package com.github.callmewaggs.stockanalyzer.model.yahoo

import com.github.callmewaggs.stockanalyzer.model.StockFetcher
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class YahooStockFetcher(private val restTemplate: RestTemplate) : StockFetcher {

    fun getHistoricalData(symbol: String): ResponseEntity<YahooHistoricalDataResponse> {
        val url = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v3/get-historical-data?region=US&symbol=$symbol"
        val httpHeaders = getHeaders()
        val request: HttpEntity<String> = HttpEntity(httpHeaders)
        return restTemplate.exchange(url, HttpMethod.GET, request, YahooHistoricalDataResponse::class.java)
    }

    private fun getHeaders(): HttpHeaders {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON;
        httpHeaders.set("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
        httpHeaders.set("x-rapidapi-key", "223e57eaabmsh01888e31746f3d6p1eb2fejsnafc30fd30384")
        return httpHeaders
    }

}