package com.blog.backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class Comment(
    author: String,
    description: String,
    post: Post,
) : BaseEntity() {

    @Column(nullable = false)
    var author: String = author
        protected set

    @Column(nullable = false)
    var description: String = description
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    var post: Post = post
        protected set
}
