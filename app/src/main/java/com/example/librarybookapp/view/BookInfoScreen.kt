package com.example.librarybookapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.librarybookapp.viewmodel.BookListViewModel
import kotlinx.coroutines.launch

@Composable
fun BookInfoScreen(bookListViewModel: BookListViewModel, onDelete: () -> Unit, onEdit: () -> Unit) {
    val currentBook = bookListViewModel.bookInfo.value
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize().padding(top = 30.dp)) {
        Text(text = "Book Title: ")
        BookInfoCard(currentBook?.bookTitle ?: "")

        Text(text = "Book Author: ")
        BookInfoCard(currentBook?.bookAuthor ?: "")

        Text(text = "Book Genre: ")
        BookInfoCard(currentBook?.bookGenre ?: "")

        Text(text = "Date Published: ")
        BookInfoCard(currentBook?.datePublished ?: "")

        Text(text = "Pages: ")
        BookInfoCard(currentBook?.readingProgress ?: "")

        Text(text = "Date Added:")
        BookInfoCard(currentBook?.dateAdded ?: "")

        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth())
        {
            Button(onClick = {onEdit()},
                modifier = Modifier.padding(top = 10.dp,
                    start = 10.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF6650a4))
            ) {
                Text(text = "Edit"
                )
            }
            Button(modifier = Modifier.padding(top = 10.dp)
                ,onClick = {
                coroutineScope.launch {
                    bookListViewModel.deleteBook(currentBook!!.id)
                }
                onDelete()
            },
                colors = ButtonDefaults.buttonColors(Color.Red)) {
                Row{
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                    Text(text = "Delete")
                }
            }
        }
    }
}