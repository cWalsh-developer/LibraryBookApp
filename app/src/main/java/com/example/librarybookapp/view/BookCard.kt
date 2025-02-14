package com.example.librarybookapp.view

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import com.example.librarybookapp.model.Book
import com.example.librarybookapp.viewmodel.BookInfoViewModel

@Composable
fun BookCard(book: Book, onInfo: () -> Unit, bookInfoViewModel: BookInfoViewModel)
{
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    )
    {
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp)){
            Text(text = "Title: ${book.bookTitle}")
            Text(text = "Author: ${book.bookAuthor}")
            Text(text = "Publish Date: ${book.dateAdded}")
        }
        IconButton(onClick = {bookInfoViewModel.setBookInfo(book)
            onInfo()})
        {
            Icon(Icons.Default.Info, contentDescription = "Info")
        }
    }
}