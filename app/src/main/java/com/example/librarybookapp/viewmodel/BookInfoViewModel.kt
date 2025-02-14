package com.example.librarybookapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.librarybookapp.model.Book

class BookInfoViewModel : ViewModel() {
    private val _bookInfo = mutableStateOf<Book?>(null)
    val bookInfo: State<Book?> = _bookInfo

    fun setBookInfo(book: Book) {
        _bookInfo.value = book
    }
}