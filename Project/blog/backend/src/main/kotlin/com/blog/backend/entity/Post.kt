package com.blog.backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

@Entity
class Post(
    title: String,
    description: String,
    comments: MutableSet<Comment> = mutableSetOf(),
) : BaseEntity() {
    @Column(nullable = false)
    var title: String = title
        protected set

    @Column(nullable = false)
    var description: String = description
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post_id")
    @JoinColumn(name = "comment")
    var comment: MutableSet<Comment> = comments
        protected set
}
