package com.example.settingproject

import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository : JpaRepository<Note, Long>
