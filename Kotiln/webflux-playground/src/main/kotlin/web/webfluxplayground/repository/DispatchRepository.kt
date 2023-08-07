package web.webfluxplayground.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import web.webfluxplayground.domain.Dispatch

interface DispatchRepository: ReactiveCrudRepository<Dispatch, Long>
