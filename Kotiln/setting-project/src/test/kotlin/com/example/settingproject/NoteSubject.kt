package com.example.settingproject

class NoteSubject {
    private var title: String = "제목"
    private var description: String = "설명"

    fun of(title: String, description: String): Note {
        return Note(title, description)
    }

    fun any(): Note {
        return Note(title, description)
    }

    fun setTitle(title: String): NoteSubject {
        this.title = title
        return this
    }

    fun setDescription(description: String): NoteSubject {
        this.description = description
        return this
    }

    fun build(): Note {
        return Note(title, description)
    }
}
