package com.jpa.kotlinjpa.facade

import com.jpa.kotlinjpa.controller.dto.EnrollDto
import com.jpa.kotlinjpa.entity.Enroll
import com.jpa.kotlinjpa.exception.ClientBadRequestException
import com.jpa.kotlinjpa.sevice.CourseService
import com.jpa.kotlinjpa.sevice.EnrollService
import com.jpa.kotlinjpa.sevice.StudentService
import com.jpa.kotlinjpa.util.CourseSubject
import com.jpa.kotlinjpa.util.EnrollSubject
import com.jpa.kotlinjpa.util.StudentSubject
import com.jpa.kotlinjpa.util.TeacherSubject
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class EnrollFacadeTest {

    @InjectMocks
    private lateinit var sut: EnrollFacade

    @Mock
    private lateinit var enrollService: EnrollService

    @Mock
    private lateinit var courseService: CourseService

    @Mock
    private lateinit var studentService: StudentService

    @Nested
    inner class `수강생을 강좌에 등록 할 때` {

        @Nested
        inner class `등록 실패한다` {

            @Test /* 안 좋은 테스트 예시 */
            fun `수강생이 등록되지 않은 경우`() {
                // given
                val givenDto = EnrollDto(10, 20)
                whenever(studentService.findByIdOrThrow(givenDto.studentId))
                        .thenThrow(ClientBadRequestException::class.java)

                // when & then
                assertThrows<ClientBadRequestException> {
                    sut.enroll(givenDto)
                }
            }

            @Test
            fun `이미 등록한 강좌인 경우`() {
                // given
                val givenCourse = CourseSubject().any()
                val givenEnroll = EnrollSubject().any()
                val givenStudent = StudentSubject().setEnrolls(mutableSetOf(givenEnroll)).build()
                val givenDto = EnrollDto(givenCourse.getId(), givenStudent.getId())
                whenever(studentService.findByIdOrThrow(givenDto.studentId))
                        .thenReturn(givenStudent)

                // when & then
                assertThrows<ClientBadRequestException> {
                    sut.enroll(givenDto)
                }
            }
        }

        @Nested
        inner class `등록 성공한다` {
            @Test
            fun `수강생과 강의가 등록된 경우`() {
                // given
                val givenCaptor = ArgumentCaptor.forClass(Enroll::class.java)
                val givenTeacher = TeacherSubject().any()
                val givenCourse = CourseSubject().setTeacher(givenTeacher).build()

                val givenEnroll = EnrollSubject().any()
                val givenStudent = StudentSubject().any()
                val givenDto = EnrollDto(givenCourse.getId(), givenStudent.getId())
                whenever(studentService.findByIdOrThrow(givenDto.studentId))
                        .thenReturn(givenStudent)
                whenever(courseService.findByIdOrThrow(givenDto.courseId))
                        .thenReturn(givenCourse)
                whenever(enrollService.enroll(any()))
                        .thenReturn(givenEnroll)

                // when
                val enroll = sut.enroll(givenDto)

                // then
                verify(enrollService, times(1)).enroll(givenCaptor.capture())
                SoftAssertions.assertSoftly { softly ->
                    softly.assertThat(givenCaptor.value.course.getId()).isEqualTo(givenDto.courseId)
                    softly.assertThat(givenCaptor.value.student.getId()).isEqualTo(givenDto.studentId)
                    softly.assertThat(enroll.course.getId()).isEqualTo(givenDto.courseId)
                    softly.assertThat(enroll.student.getId()).isEqualTo(givenDto.studentId)
                }
            }
        }
    }
}
