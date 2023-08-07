package web.webfluxplayground.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import web.webfluxplayground.domain.Driving

interface DrivingRepository : ReactiveCrudRepository<Driving, Long>
