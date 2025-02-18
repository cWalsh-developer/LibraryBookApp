package com.example.librarybookapp.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddBookScreen(onBookAdded: () -> Unit, bookListViewModel: BookListViewModel) {
    var bookTitle by remember { mutableStateOf("") }
    var bookAuthor by remember { mutableStateOf("") }
    var bookGenre by remember { mutableStateOf("") }
    var bookProgress by remember { mutableStateOf("") }
    var bookDate by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize().verticalScroll(rememberScrollState())
            .padding(16.dp).imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Book",
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color(0xFF6650a4),
            style = MaterialTheme.typography.headlineLarge
        )

        OutlinedTextField(
            value = bookTitle,
            onValueChange = { bookTitle = it },
            isError = (bookTitle.isEmpty()&&isError),
            trailingIcon = {if(bookTitle.isEmpty()&&isError)
            {
                Icon(Icons.Default.Warning, contentDescription = "Error")
            }},
            supportingText = {if(bookTitle.isEmpty()&&isError)
            {
                Text(text = "Please enter a title")
            }},
            label = { Text("Book Title")},
            modifier = Modifier.padding(8.dp).fillMaxWidth())

        OutlinedTextField(
            value = bookAuthor,
            onValueChange = { bookAuthor = it },
            isError = (bookAuthor.isEmpty()&&isError),
            trailingIcon = {if(bookAuthor.isEmpty()&&isError)
            {
                Icon(Icons.Default.Warning, contentDescription = "Error")
            }},
            supportingText = {if(bookAuthor.isEmpty()&&isError)
            {
                Text(text = "Please enter an author")
            }},
            label = { Text("Book Author")},
            modifier = Modifier.padding(8.dp).fillMaxWidth())

        OutlinedTextField(
            value = bookGenre,
            onValueChange = { bookGenre = it },
            isError = (bookGenre.isEmpty()&&isError),
            trailingIcon = {if(bookGenre.isEmpty()&&isError)
            {
                Icon(Icons.Default.Warning, contentDescription = "Error")
            }},
            supportingText = {if(bookGenre.isEmpty()&&isError)
            {
                Text(text = "Please enter a genre")
            }},
            label = { Text("Book Genre")},
            modifier = Modifier.padding(8.dp).fillMaxWidth())


        OutlinedTextField(
            value = bookProgress,
            onValueChange = { bookProgress = it },
            isError = (bookProgress.isEmpty()&&isError),
            trailingIcon = {if(bookProgress.isEmpty()&&isError)
            {
                Icon(Icons.Default.Warning, contentDescription = "Error")
            }},
            supportingText = {if(bookProgress.isEmpty()&&isError)
            {
                Text(text = "Please enter a number of pages")
            }},
            label = { Text("Number of Pages")},
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            )

        OutlinedTextField(
            value = bookDate,
            onValueChange = { bookDate = it },
            isError = (bookDate.isEmpty()&&isError),
            trailingIcon = {if(bookDate.isEmpty()&&isError)
            {
                Icon(Icons.Default.Warning, contentDescription = "Error")
            }},
            supportingText = {if(bookDate.isEmpty()&&isError)
            {
                Text(text = "Please enter a date")
            }},
            placeholder = { Text("dd/MM/yyyy")},
            label = { Text("Book Date")},
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
        )

        Button(onClick = {
            // Add book to database or perform other actions
            if (bookTitle.isEmpty() || bookAuthor.isEmpty() || bookGenre.isEmpty() ||
                bookProgress.isEmpty() || bookDate.isEmpty()) {
                isError = true
                return@Button
            }
            val dateAdded = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            val book = Book(bookTitle = bookTitle,
                bookAuthor = bookAuthor,
                bookGenre = bookGenre,
                datePublished = bookDate,
                dateAdded = dateAdded,
                readingProgress = bookProgress)
            coroutineScope.launch {
                bookListViewModel.addBook(book)
            }
            bookTitle = ""
            bookAuthor = ""
            bookGenre = ""
            bookProgress = ""
            bookDate = ""
            isError = false
            onBookAdded()},
            ) {
            Text(text = "Add Book")
        }
    }
}