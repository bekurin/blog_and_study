package com.jpa.kotlinjpa.sevice

import com.jpa.kotlinjpa.entity.Enroll
import com.jpa.kotlinjpa.exception.ResourceNotFoundException
import com.jpa.kotlinjpa.repository.EnrollRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class EnrollService(
        private val enrollRepository: EnrollRepository
) {
    @Transactional
    fun enroll(enroll: Enroll): Enroll {
        return enrollRepository.save(enroll)
    }

    fun findById(id: Long): Enroll {
        return enrollRepository.findById(id)
                .orElseThrow { throw ResourceNotFoundException("등록 내역을 찾을 수 없습니다 (id=$id)") }
    }
}
