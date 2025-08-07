package stock.modules.web.service

import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import stock.modules.web.dto.StockPriceResponse

interface StockPriceApiClient {
    @GetExchange("/getStockPriceInfo")
    fun getStockPriceInfo(
        @RequestParam serviceKey: String,
        @RequestParam numOfRows: Int = 1000,
        @RequestParam pageNo: Int = 1,
        @RequestParam resultType: String = "json",
        @RequestParam basDt: String? = null,
        @RequestParam mrktCls: String? = null
    ): StockPriceResponse
} 