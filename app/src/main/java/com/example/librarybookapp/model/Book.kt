package com.example.librarybookapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//Entity for the database with the book table and automation generated id
@Entity(tableName = "books")
data class Book(
    var bookTitle: String,
    var bookAuthor: String,
    var bookGenre: String,
    var datePublished: String,
    var dateAdded: String,
    var pages: Int,
    var progress: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0)