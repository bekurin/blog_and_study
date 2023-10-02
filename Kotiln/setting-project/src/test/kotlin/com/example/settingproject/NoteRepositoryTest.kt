package com.example.settingproject

import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class NoteRepositoryTest : IntegrationTestBase() {

    @Autowired
    private lateinit var noteRepository: NoteRepository

    @Test
    fun `노트를 저장한다`() {
        // given
        val givenNote = NoteSubject().any()

        // when
        val savedNote = noteRepository.save(givenNote)

        // then
        val foundNote = noteRepository.findById(savedNote.id).get()
        SoftAssertions.assertSoftly { softly ->
            softly.assertThat(savedNote.id).isEqualTo(foundNote.id)
            softly.assertThat(savedNote.title).isEqualTo(foundNote.title)
            softly.assertThat(savedNote.description).isEqualTo(foundNote.description)
        }
    }
}
