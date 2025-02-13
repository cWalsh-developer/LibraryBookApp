package com.example.librarybookapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.librarybookapp.model.Book
import com.example.librarybookapp.model.BookDAO


class BookListViewModel(private val bookDao: BookDAO): ViewModel() {
    suspend fun getBooks(): List<Book> {
        return bookDao.getAllBooks()
    }

    suspend fun addBook(book: Book) {
        bookDao.insertBook(book)
    }
}