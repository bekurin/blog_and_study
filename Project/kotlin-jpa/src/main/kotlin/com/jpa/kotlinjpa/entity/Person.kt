package com.jpa.kotlinjpa.entity

import jakarta.persistence.Entity

@Entity
class Person(
        name: String,
        age: Int
) : PrimaryKeyEntity() {
    init {
        if (age < 1) {
            throw IllegalArgumentException("나이는 1살 이상이어야합니다.")
        }
    }

    var name: String = name
        protected set

    var age: Int = age
        protected set
}
