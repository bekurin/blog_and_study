package com.example.settingproject

data class NoteResponse(
    val id: Long,
    val title: String,
    val description: String
) {
    constructor(entity: Note) : this(
        id = entity.id,
        title = entity.title,
        description = entity.description
    )
}

data class NoteCreationRequest(
    val title: String,
    val description: String
) {
    fun toEntity(): Note {
        return Note(title, description)
    }
}

data class NoteUpdateRequest(
    val title: String,
    val description: String
)


