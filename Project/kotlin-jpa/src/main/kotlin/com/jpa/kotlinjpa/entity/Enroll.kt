package com.jpa.kotlinjpa.entity

import jakarta.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(name = "unique_enroll_uk01", columnNames = ["course_id", "student_id"])])
class Enroll(
        course: Course,
        student: Student
) : PrimaryKeyEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    var course: Course = course
        protected set

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "student_id")
    var student: Student = student

    fun updateCourse(course: Course) {
        this.course = course
    }

    fun updateStudent(student: Student) {
        this.student.enrolls.remove(this)
        this.student = student
        student.enrolls.add(this)
    } 
}
