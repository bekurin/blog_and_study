package com.jpa.kotlinjpa.util

import com.jpa.kotlinjpa.entity.Course
import com.jpa.kotlinjpa.entity.Enroll
import com.jpa.kotlinjpa.entity.Student
import com.jpa.kotlinjpa.entity.Teacher
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import com.navercorp.fixturemonkey.kotlin.setExp

object Fixture {
    private val fixtureMonkey: FixtureMonkey = FixtureMonkey.builder().plugin(KotlinPlugin()).build()

    object StudentFixture {
        fun anyStudent(): Student {
            return fixtureMonkey.giveMeOne()
        }

        fun enrolledStudent(enrolls: List<Enroll> = listOf()): Student {
            return fixtureMonkey.giveMeBuilder<Student>()
                    .setExp(Student::enrolls, enrolls)
                    .sample()
        }
    }

    object TeacherFixture {
        fun anyTeacher(): Teacher {
            return fixtureMonkey.giveMeOne()
        }
    }

    object EnrollFixture {
        fun anyEnroll(): Enroll {
            return fixtureMonkey.giveMeOne()
        }
    }

    object CourseFixture {
        fun anyCourse(): Course {
            return fixtureMonkey.giveMeOne()
        }
    }
}
