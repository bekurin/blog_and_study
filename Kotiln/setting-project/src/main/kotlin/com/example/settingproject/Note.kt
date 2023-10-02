package com.example.settingproject

import jakarta.persistence.*

@Entity
class Note(
    title: String,
    description: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        protected set

    @Column(nullable = false, length = 50)
    var title: String = title
        protected set

    @Column(nullable = false, length = 255)
    var description: String = description
        protected set

    fun updateTitleAndDescription(title: String, description: String): Note {
        this.title = title
        this.description = description
        return this
    }
}
