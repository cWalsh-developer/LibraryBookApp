package com.example.librarybookapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.librarybookapp.model.AppDatabase
import com.example.librarybookapp.model.Book
import com.example.librarybookapp.model.BookDAO


class BookListViewModel(private val database: AppDatabase): ViewModel() {
    private val bookDao: BookDAO = database.bookDao()

    private val _bookInfo = mutableStateOf<Book?>(null)
    val bookInfo: State<Book?> = _bookInfo

    fun setBookInfo(book: Book) {
        _bookInfo.value = book
    }

    suspend fun getBooks(): List<Book> {
        return bookDao.getAllBooks()
    }

    suspend fun addBook(book: Book) {
        bookDao.insertBook(book)
    }

    suspend fun deleteBook(id: Int) {
        bookDao.deleteBook(id)
    }

    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }
}