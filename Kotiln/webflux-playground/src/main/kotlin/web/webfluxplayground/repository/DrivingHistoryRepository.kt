package web.webfluxplayground.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import web.webfluxplayground.domain.DrivingHistory

interface DrivingHistoryRepository : ReactiveCrudRepository<DrivingHistory, Long>
