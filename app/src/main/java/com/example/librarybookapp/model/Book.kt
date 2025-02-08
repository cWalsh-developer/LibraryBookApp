package com.example.librarybookapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(val bookTitle: String,
                val bookAuthor: String,
                val bookGenre: String,
                val dateAdded: String,
                val readingProgress: String,
                @PrimaryKey(autoGenerate = true) val id: Int = 0)