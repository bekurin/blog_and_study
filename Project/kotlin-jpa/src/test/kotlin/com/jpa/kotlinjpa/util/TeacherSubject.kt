package com.jpa.kotlinjpa.util

import com.jpa.kotlinjpa.entity.Course
import com.jpa.kotlinjpa.entity.Teacher

class TeacherSubject {
    private var name: String = "홍홍홍"
    private var courses: MutableSet<Course> = mutableSetOf()

    fun of(name: String, courses: MutableSet<Course>): Teacher {
        return Teacher(name, courses)
    }

    fun any(): Teacher {
        return Teacher(name)
    }

    fun setName(name: String): TeacherSubject {
        this.name = name
        return this
    }

    fun setCourses(courses: MutableSet<Course>): TeacherSubject {
        this.courses = courses
        return this
    }

    fun build(): Teacher {
        return Teacher(name, courses)
    }
}
