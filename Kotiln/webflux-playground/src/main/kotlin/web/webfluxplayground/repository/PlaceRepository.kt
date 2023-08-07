package web.webfluxplayground.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import web.webfluxplayground.domain.Place

interface PlaceRepository : ReactiveCrudRepository<Place, Long>
