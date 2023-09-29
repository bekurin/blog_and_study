package com.jpa.kotlinjpa.util

import com.jpa.kotlinjpa.entity.Course
import com.jpa.kotlinjpa.entity.Teacher

class CourseSubject {
    private var teacher: Teacher = TeacherSubject().any()
    private var name: String = "수영 1반"
    private var capacity: Int = 10

    fun of(teacher: Teacher, name: String, capacity: Int): Course {
        return Course(teacher, name, capacity)
    }

    fun any(): Course {
        return Course(teacher, name, capacity)
    }

    fun setTeacher(teacher: Teacher): CourseSubject {
        this.teacher = teacher
        return this
    }

    fun setName(name: String): CourseSubject {
        this.name = name
        return this
    }

    fun setCapacity(capacity: Int): CourseSubject {
        this.capacity = capacity
        return this
    }

    fun build(): Course {
        return Course(teacher, name, capacity)
    }
}
