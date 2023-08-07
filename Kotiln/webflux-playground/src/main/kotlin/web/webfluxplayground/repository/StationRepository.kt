package web.webfluxplayground.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import web.webfluxplayground.domain.Station

interface StationRepository : ReactiveCrudRepository<Station, Long>
