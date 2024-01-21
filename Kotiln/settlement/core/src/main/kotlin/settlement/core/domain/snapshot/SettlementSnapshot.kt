package settlement.core.domain.snapshot

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Converter
import jakarta.persistence.Entity
import jakarta.persistence.FetchType.LAZY
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import settlement.core.domain.BaseEntity

@Entity
class SettlementSnapshot(
    settlementSummary: SettlementSummary,
    dailySettlements: MutableList<DailySettlement>,
) : BaseEntity() {
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "settlement_summary_id")
    var settlementSummary = settlementSummary
        protected set

    @Column(nullable = false, columnDefinition = "json")
    @Convert(converter = DailySettlementsConverter::class)
    var dailySettlements: MutableList<DailySettlement> = dailySettlements
        protected set
}

@Converter
class DailySettlementsConverter : AttributeConverter<List<DailySettlement>, String> {
    private val objectMapper = jacksonObjectMapper()
    override fun convertToDatabaseColumn(attribute: List<DailySettlement>): String {
        return objectMapper.writeValueAsString(attribute)
    }
    override fun convertToEntityAttribute(dbData: String): List<DailySettlement> {
        return objectMapper.readValue<List<DailySettlement>>(dbData)
    }
}

data class DailySettlement(
    val day: Int,
    val unitPrice: Int,
    val totalShippingCount: Int,
)
