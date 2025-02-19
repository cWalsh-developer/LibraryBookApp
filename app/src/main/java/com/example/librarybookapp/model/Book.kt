package com.example.librarybookapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "books")
data class Book(
    var bookTitle: String,
    var bookAuthor: String,
    var bookGenre: String,
    var datePublished: String,
    var dateAdded: String,
    var pages: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0)