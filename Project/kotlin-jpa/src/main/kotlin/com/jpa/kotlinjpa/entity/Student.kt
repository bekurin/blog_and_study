package com.jpa.kotlinjpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany

@Entity
class Student(
        name: String,
        email: String,
        phone: String,
        enrolls: MutableSet<Enroll> = mutableSetOf()
) : PrimaryKeyEntity() {
    @Column(nullable = false, length = 50)
    var name: String = name
        protected set

    @Column(nullable = false, length = 100)
    var email: String = email
        protected set

    @Column(nullable = false, length = 100)
    var phone: String = phone
        protected set

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
    var enrolls: MutableSet<Enroll> = enrolls

    fun update(email: String) {
        this.email = email
    }
}
