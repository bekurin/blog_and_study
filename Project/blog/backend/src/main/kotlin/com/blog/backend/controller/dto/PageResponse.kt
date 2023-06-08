package com.blog.backend.controller.dto

import org.springframework.data.domain.Page

data class PageResponse<T>(
    val totalCount: Long,
    val currentPage: Int,
    val nextPage: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val contents: List<T>,
) {
    constructor(page: Page<T>) : this(
        totalCount = page.totalElements,
        currentPage = page.number,
        nextPage = page.number + 1,
        hasNext = page.hasNext(),
        hasPrevious = page.hasPrevious(),
        contents = page.content
    )
}
