package com.example.librarybookapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.librarybookapp.viewmodel.BookInfoViewModel

@Composable
fun BookInfoScreen(bookInfoViewModel: BookInfoViewModel) {
    val currentBook = bookInfoViewModel.bookInfo.value
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            Text(text = "Book Title: ")
            Text(text = currentBook?.bookTitle ?: "")

        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            Text(text = "Book Author: ")
            Text(text = currentBook?.bookAuthor ?: "")

        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            Text(text = "Book Genre: ")
            Text(text = currentBook?.bookGenre ?: "")

        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            Text(text = "Date Published: ")
            Text(text = currentBook?.dateAdded ?: "")

        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        {
            Text(text = "Pages: ")
            Text(text = currentBook?.readingProgress ?: "")

        }
    }
}