package com.jpa.kotlinjpa.util

import com.jpa.kotlinjpa.entity.Course
import com.jpa.kotlinjpa.entity.Enroll
import com.jpa.kotlinjpa.entity.Student

class EnrollSubject {
    private var course: Course = CourseSubject().any()
    private var student: Student = StudentSubject().any()

    fun of(course: Course, student: Student): Enroll {
        return Enroll(course, student)
    }

    fun any(): Enroll {
        return Enroll(course, student)
    }

    fun setCourse(course: Course): EnrollSubject {
        this.course = course
        return this
    }

    fun setStudent(student: Student): EnrollSubject {
        this.student = student
        return this
    }

    fun build(): Enroll {
        return Enroll(course, student)
    }
}
