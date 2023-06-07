package com.blog.backend.entity

import jakarta.persistence.*

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post", cascade = [CascadeType.ALL])
    var comment: MutableSet<Comment> = comments
        protected set
}
