package web.webfluxplayground.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import web.webfluxplayground.domain.RouteMold

interface RouteMoldRepository : ReactiveCrudRepository<RouteMold, Long>
