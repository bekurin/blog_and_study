package com.jpa.kotlinjpa.entity

import jakarta.persistence.*

@Entity
class Course(
        teacher: Teacher,
        name: String,
        capacity: Int,
) : PrimaryKeyEntity() {
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "teacher_id")
    var teacher: Teacher = teacher
        protected set

    @Column(nullable = false, length = 50)
    var name: String = name
        protected set

    @Column(nullable = false)
    var capacity: Int = capacity
        protected set

    fun updateTeacher(teacher: Teacher) {
        this.teacher.courses.remove(this)
        this.teacher = teacher
        teacher.courses.add(this)
    }
}
