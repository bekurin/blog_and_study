package web.webfluxplayground.domain

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Converter
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
class Driving(
    drivingAt: LocalDateTime,
    routeId: String,
    drivingRecords: List<DrivingRecord>
) : BaseEntity() {
    @Column(nullable = false)
    var drivingAt: LocalDateTime = drivingAt
        protected set

    @Column(nullable = false)
    var routeId: String = routeId
        protected set

    @Column(nullable = false, columnDefinition = "json")
    @Convert(converter = DrivingRecordConverter::class)
    var drivingRecords: List<DrivingRecord> = drivingRecords
        protected set
}

data class DrivingRecord(
    val stations: List<Station>,
    val dispatches: List<Dispatch>
)

@Converter
class DrivingRecordConverter : AttributeConverter<List<DrivingRecord>, String> {
    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: List<DrivingRecord>): String {
        return try {
            objectMapper.writeValueAsString(attribute)
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
            "[]"
        }
    }

    override fun convertToEntityAttribute(dbData: String): List<DrivingRecord> {
        return try {
            objectMapper.readValue(dbData, object : TypeReference<List<DrivingRecord>>() {})
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
            emptyList()
        }
    }
}

