package com.example.librarybookapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

@Composable
fun AddBookScreen(onBookAdded: () -> Unit, bookListViewModel: BookListViewModel) {
    var bookTitle by remember { mutableStateOf("") }
    var bookAuthor by remember { mutableStateOf("") }
    var bookGenre by remember { mutableStateOf("") }
    var bookProgress by remember { mutableIntStateOf(0) }
    var bookDate by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var isDateError by remember { mutableStateOf(false) }
    var datePickerDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .imePadding(),
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
            isError = (bookTitle.isEmpty() && isError),
            trailingIcon = {
                if (bookTitle.isEmpty() && isError) {
                    Icon(Icons.Default.Warning, contentDescription = "Error")
                }
            },
            supportingText = {
                if (bookTitle.isEmpty() && isError) {
                    Text(text = "Please enter a title")
                }
            },
            label = { Text("Book Title") },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = bookAuthor,
            onValueChange = { bookAuthor = it },
            isError = (bookAuthor.isEmpty() && isError),
            trailingIcon = {
                if (bookAuthor.isEmpty() && isError) {
                    Icon(Icons.Default.Warning, contentDescription = "Error")
                }
            },
            supportingText = {
                if (bookAuthor.isEmpty() && isError) {
                    Text(text = "Please enter an author")
                }
            },
            label = { Text("Book Author") },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = bookGenre,
            onValueChange = { bookGenre = it },
            isError = (bookGenre.isEmpty() && isError),
            trailingIcon = {
                if (bookGenre.isEmpty() && isError) {
                    Icon(Icons.Default.Warning, contentDescription = "Error")
                }
            },
            supportingText = {
                if (bookGenre.isEmpty() && isError) {
                    Text(text = "Please enter a genre")
                }
            },
            label = { Text("Book Genre") },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = if (bookProgress == 0) "" else bookProgress.toString(),
            onValueChange = { bookProgress = it.toIntOrNull() ?: 0 },
            isError = (bookProgress == 0 && isError),
            trailingIcon = {
                if (bookProgress == 0 && isError) {
                    Icon(Icons.Default.Warning, contentDescription = "Error")
                }
            },
            supportingText = {
                if (bookProgress == 0 && isError) {
                    Text(text = "Please enter a number of pages")
                }
            },
            label = { Text("Number of Pages") },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )

        OutlinedTextField(
            value = bookDate,
            onValueChange = {
                bookDate = it
                isDateError = !bookListViewModel.isValidDate(it)
            },
            isError = isDateError || (bookDate.isEmpty() && isError),
            trailingIcon = {
                IconButton(onClick = { datePickerDialog = true }) {
                    Icon(Icons.Filled.DateRange, contentDescription = "Date")
                }
                if ((bookDate.isEmpty() && isError) || isDateError) {
                    Icon(Icons.Default.Warning, contentDescription = "Error")
                }
            },
            supportingText = {
                if (bookDate.isEmpty() && isError) {
                    Text(text = "Please enter a date")
                }
                if (isDateError) {
                    Text(text = "Invalid date format. Use dd/MM/yyyy")
                }
            },
            placeholder = { Text("dd/MM/yyyy") },
            label = { Text("Book Date") },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
        if (datePickerDialog) {
            // Show date picker dialog
            ShowDatePicker { selectedDate ->
                bookDate = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                datePickerDialog = false
            }
        }

        Button(onClick = {
            // Add book to database or perform other actions
            if (bookTitle.isEmpty() || bookAuthor.isEmpty() || bookGenre.isEmpty() ||
                bookProgress == 0 || bookDate.isEmpty() || isDateError
            ) {
                isError = true
                return@Button
            }
            val dateAdded = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            val book = Book(
                bookTitle = bookTitle,
                bookAuthor = bookAuthor,
                bookGenre = bookGenre,
                datePublished = bookDate,
                dateAdded = dateAdded,
                pages = bookProgress
            )
            coroutineScope.launch {
                bookListViewModel.addBook(book)
            }
            bookTitle = ""
            bookAuthor = ""
            bookGenre = ""
            bookProgress = 0
            bookDate = ""
            isError = false
            isDateError = false
            onBookAdded()
        }) {
            Text(text = "Add Book")
        }
    }
}