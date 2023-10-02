package com.example.settingproject

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class NoteService(
    private val noteRepository: NoteRepository
) {
    @Transactional
    fun createNote(note: Note): Note {
        return noteRepository.save(note)
    }

    @Transactional
    fun updateNote(id: Long, request: NoteUpdateRequest): Note {
        val note = findByIdOrThrow(id)
        return note.updateTitleAndDescription(request.title, request.description)
    }

    @Transactional
    fun deleteNote(id: Long) {
        val note = findByIdOrThrow(id)
        noteRepository.delete(note)
    }

    fun findAllNote(): List<Note> {
        return noteRepository.findAll()
    }

    private fun findByIdOrThrow(id: Long): Note {
        return noteRepository.findById(id)
            .orElseThrow { IllegalStateException("노트 정보를 찾을 수 없습니다. (id=$id)") }
    }
}
