package web.webfluxplayground.domain

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
class DrivingHistory(
    drivingAt: LocalDateTime,
    routId: String,
    passenger: Int,
    drivingRecords: List<DrivingRecord>
) : BaseEntity() {
    @Column(nullable = false)
    var drivingAt: LocalDateTime = drivingAt
        protected set

    @Column(nullable = false)
    var routeId: String = routId
        protected set

    @Column(nullable = false)
    var passenger: Int = passenger
        protected set

    @Column(nullable = false, columnDefinition = "json")
    @Convert(converter = DrivingRecordConverter::class)
    var drivingRecords: List<DrivingRecord> = drivingRecords
        protected set
}
