package com.example.librarybookapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.librarybookapp.model.Book
import com.example.librarybookapp.viewmodel.BookListViewModel
import kotlinx.coroutines.launch

@Composable
fun EditBookScreen(bookListViewModel: BookListViewModel, onSaveChanges: () -> Unit)
{
    val currentBook = bookListViewModel.bookInfo.value
    var bookTitle by remember { mutableStateOf(currentBook!!.bookTitle) }
    var bookAuthor by remember { mutableStateOf(currentBook!!.bookAuthor) }
    var bookGenre by remember { mutableStateOf(currentBook!!.bookGenre) }
    var bookProgress by remember { mutableStateOf(currentBook!!.readingProgress) }
    var datePublished by remember { mutableStateOf(currentBook!!.datePublished) }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize().verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Book",
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color(0xFF6650a4),
            style = MaterialTheme.typography.headlineLarge
        )

        OutlinedTextField(
            value = bookTitle,
            onValueChange = { bookTitle = it },
            label = { Text("Book Title") },
            modifier = Modifier.padding(8.dp).fillMaxWidth())

        OutlinedTextField(
            value = bookAuthor,
            onValueChange = { bookAuthor = it },
            label = { Text("Book Author") },
            modifier = Modifier.padding(8.dp).fillMaxWidth())

        OutlinedTextField(
            value = bookGenre,
            onValueChange = { bookGenre = it },
            label = { Text("Book Genre") },
            modifier = Modifier.padding(8.dp).fillMaxWidth())


        OutlinedTextField(
            value = bookProgress,
            onValueChange = { bookProgress = it },
            label = { Text("Book Progress") },
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
        )

        OutlinedTextField(
            value = datePublished,
            onValueChange = { datePublished = it },
            placeholder = { Text("dd/MM/yyyy") },
            label = { Text("Book Date") },
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
        )

        Button(onClick = {
            // Add book to database or perform other actions
            val book = Book(bookTitle = bookTitle,
                bookAuthor = bookAuthor,
                bookGenre = bookGenre,
                datePublished = datePublished,
                dateAdded = currentBook!!.dateAdded,
                readingProgress = bookProgress,
                id = currentBook.id)
            coroutineScope.launch {
                bookListViewModel.updateBook(book)
            }
            bookTitle = ""
            bookAuthor = ""
            bookGenre = ""
            bookProgress = ""
            datePublished = ""

            onSaveChanges()
        })
        {
            Text(text = "Save Changes")
        }
    }
}