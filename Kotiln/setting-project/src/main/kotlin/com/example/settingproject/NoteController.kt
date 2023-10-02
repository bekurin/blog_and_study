package com.example.settingproject

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class NoteController(
    private val noteService: NoteService
) {
    @GetMapping("/notes/page/{page}")
    fun findPagedNote(): List<NoteResponse> {
        return noteService.findAllNote()
            .map { NoteResponse(it) }
    }

    @PostMapping("/notes")
    @ResponseStatus(HttpStatus.CREATED)
    fun createNote(
        @RequestBody request: NoteCreationRequest
    ): NoteResponse {
        val savedNote = noteService.createNote(request.toEntity())
        return NoteResponse(savedNote)
    }

    @PutMapping("/notes/{id}")
    fun updateNote(
        @PathVariable id: Long,
        @RequestBody request: NoteUpdateRequest
    ): NoteResponse {
        val updatedNote = noteService.updateNote(id, request)
        return NoteResponse(updatedNote)
    }

    @DeleteMapping("/notes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteNote(
        @PathVariable id: Long
    ) {
        noteService.deleteNote(id)
    }
}
