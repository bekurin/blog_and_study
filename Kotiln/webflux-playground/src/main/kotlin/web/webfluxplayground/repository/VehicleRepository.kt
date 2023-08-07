package web.webfluxplayground.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import web.webfluxplayground.domain.Vehicle

interface VehicleRepository : ReactiveCrudRepository<Vehicle, Long>
