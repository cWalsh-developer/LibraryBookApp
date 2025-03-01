package com.example.librarybookapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//Data access object for the book database
@Dao
interface BookDAO {
    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Query("DELETE FROM books WHERE id = :id")
    suspend fun deleteBook(id: Int)

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBookById(id: Int): Book?

    @Query("SELECT * FROM books WHERE bookTitle = :bookTitle AND bookAuthor = :bookAuthor")
    fun getBookByTitle(bookTitle: String, bookAuthor: String): Flow<List<Book?>>

    @Update
    suspend fun updateBook(book: Book)
}