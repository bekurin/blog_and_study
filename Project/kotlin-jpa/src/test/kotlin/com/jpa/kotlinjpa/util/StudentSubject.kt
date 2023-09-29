package com.jpa.kotlinjpa.util

import com.jpa.kotlinjpa.entity.Enroll
import com.jpa.kotlinjpa.entity.Student

class StudentSubject {
    private var name: String = "홍길동"
    private var email: String = "test@gmail.com"
    private var phone: String = "01012394031"
    private var enrolls: MutableSet<Enroll> = mutableSetOf()

    fun of(name: String, email: String, phone: String, enrolls: MutableSet<Enroll>): Student {
        return Student(name, email, phone, enrolls)
    }

    fun any(): Student {
        return Student(name, email, phone, enrolls)
    }

    fun setName(name: String): StudentSubject {
        this.name = name
        return this
    }

    fun setEmail(email: String): StudentSubject {
        this.email = email
        return this
    }

    fun setPhone(phone: String): StudentSubject {
        this.phone = phone
        return this
    }

    fun setEnrolls(enrolls: MutableSet<Enroll>): StudentSubject {
        this.enrolls = enrolls
        return this
    }

    fun build(): Student {
        return Student(name, email, phone, enrolls)
    }
}
