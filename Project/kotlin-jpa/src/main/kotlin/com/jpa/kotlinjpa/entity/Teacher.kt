package com.jpa.kotlinjpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany

@Entity
class Teacher(
        name: String,
        courses: MutableSet<Course> = mutableSetOf()
) : PrimaryKeyEntity() {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher")
    var courses: MutableSet<Course> = courses
        protected set

    @Column(nullable = false, length = 50)
    var name: String = name
        protected set
}
