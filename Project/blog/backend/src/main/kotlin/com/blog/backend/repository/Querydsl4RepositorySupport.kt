package com.blog.backend.repository

import com.querydsl.jpa.JPQLQuery
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.Querydsl
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class Querydsl4RepositorySupport(
    klass: Class<*>,
) : QuerydslRepositorySupport(klass) {

    override fun getQuerydsl(): Querydsl {
        val querydsl = super.getQuerydsl()
        checkNotNull(querydsl)
        return querydsl
    }

    fun <T> getSortedAndPaginatedResultsAsPage(query: JPQLQuery<T>, pageable: Pageable): Page<T> {
        val results = querydsl.applySorting(pageable.sort, query)
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()
        return PageImpl(results, pageable, query.fetchCount())
    }

    fun <T> getPaginatedResultsAsPage(query: JPQLQuery<T>, pageable: Pageable): Page<T> {
        val results = querydsl.applyPagination(pageable, query)
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()
        return PageImpl(results, pageable, query.fetchCount())
    }
}
