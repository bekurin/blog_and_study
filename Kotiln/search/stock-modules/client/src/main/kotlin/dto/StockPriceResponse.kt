package stock.modules.web.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class StockPriceResponse(
    @JsonProperty("response") val response: StockPriceResponseBody?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class StockPriceResponseBody(
    @JsonProperty("body") val body: StockPriceBody?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class StockPriceBody(
    @JsonProperty("items") val items: StockPriceItems?,
    @JsonProperty("totalCount") val totalCount: Int? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class StockPriceItems(
    @JsonProperty("item") val item: List<StockPriceItem> = emptyList()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class StockPriceItem(
    @JsonProperty("basDt") val basDt: String?,
    @JsonProperty("srtnCd") val srtnCd: String?,
    @JsonProperty("isinCd") val isinCd: String?,
    @JsonProperty("itmsNm") val itmsNm: String?,
    @JsonProperty("mrktCtg") val mrktCtg: String?,
    @JsonProperty("clpr") val clpr: String?,
    @JsonProperty("vs") val vs: String?,
    @JsonProperty("fltRt") val fltRt: String?,
    @JsonProperty("mkp") val mkp: String?,
    @JsonProperty("hipr") val hipr: String?,
    @JsonProperty("lopr") val lopr: String?,
    @JsonProperty("trqu") val trqu: String?,
    @JsonProperty("trPrc") val trPrc: String?,
    @JsonProperty("lstgStCnt") val lstgStCnt: String?,
    @JsonProperty("mrktTotAmt") val mrktTotAmt: String?
) 