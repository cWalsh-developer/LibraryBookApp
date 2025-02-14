package com.example.librarybookapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.librarybookapp.model.AppDatabase
import com.example.librarybookapp.model.Book
import com.example.librarybookapp.model.BookDAO


class BookListViewModel(private val database: AppDatabase): ViewModel() {
    private val bookDao: BookDAO = database.bookDao()
    suspend fun getBooks(): List<Book> {
        return bookDao.getAllBooks()
    }

    suspend fun addBook(book: Book) {
        bookDao.insertBook(book)
    }
}