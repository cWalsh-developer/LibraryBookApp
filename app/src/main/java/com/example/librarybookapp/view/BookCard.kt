package com.example.librarybookapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.librarybookapp.model.Book
import com.example.librarybookapp.viewmodel.BookInfoViewModel
import com.example.librarybookapp.viewmodel.BookListViewModel

@Composable
fun BookCard(book: Book, onInfo: () -> Unit, bookListViewModel: BookListViewModel)
{
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp).clickable { bookListViewModel.setBookInfo(book)
                                     onInfo() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF6650a4))
    )
    {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp).background(Color(0xFF6650a4))){
            Text(text = "Title: ${book.bookTitle}\n" +
                    "Author: ${book.bookAuthor}\n" +
                    "Publish Date: ${book.datePublished}",
                color = Color.White)
        }
    }
}