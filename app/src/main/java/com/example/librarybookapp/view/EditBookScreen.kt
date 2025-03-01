package com.example.librarybookapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import java.time.format.DateTimeFormatter

@Composable
fun EditBookScreen(bookListViewModel: BookListViewModel, onSaveChanges: () -> Unit)
{

    val currentBook = bookListViewModel.bookInfo.value
    //state variables
    var bookTitle by remember { mutableStateOf(currentBook!!.bookTitle) }
    var bookAuthor by remember { mutableStateOf(currentBook!!.bookAuthor) }
    var bookGenre by remember { mutableStateOf(currentBook!!.bookGenre) }
    var bookProgress by remember { mutableStateOf<Int?>(currentBook!!.progress) }
    var bookPages by remember { mutableStateOf<Int?>(currentBook!!.pages) }
    var isError by remember { mutableStateOf(false) }
    var isDateError by remember { mutableStateOf(false) }
    var datePickerDialog by remember { mutableStateOf(false) }
    var isProgressError by remember { mutableStateOf(false) }
    var bookDate by remember { mutableStateOf(currentBook!!.datePublished) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize().verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Page title
        Text(
            text = "Edit Book",
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color(0xFF6650a4),
            style = MaterialTheme.typography.headlineLarge
        )
        // Input field for book title
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
        // Input field for book author
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
        // Input field for book genre
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
        // Input field for book progress with extensive error handling
        OutlinedTextField(
            value = if (bookProgress == null) "" else bookProgress.toString(),
            onValueChange = { bookProgress = it.toIntOrNull()
                isProgressError = false},
            isError = (bookProgress == null && isError|| isProgressError),
            trailingIcon = {
                if (bookProgress == null && isProgressError || bookProgress == null && isError
                    || bookProgress != null && bookPages!= null &&
                    bookProgress!!> bookPages!!) {
                    Icon(Icons.Default.Warning, contentDescription = "Error")
                }
            },
            supportingText = {
                if (bookProgress == null && isError)
                    Text(text = "Please enter a number of pages read")
                if(bookProgress != null && bookPages!= null && bookProgress!! > bookPages!!){
                    Text(text = "Number of pages read cannot be greater than number of pages",)
                    isProgressError = true
                }
            },
            label = { Text("Number of Pages Read") },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
        // Input field for number of book pages
        OutlinedTextField(
            value = if (bookPages == null) "" else bookPages.toString(),
            onValueChange = { bookPages = it.toIntOrNull()
                            isProgressError = false},
            isError = (bookPages == null && isError),
            trailingIcon = {
                if (bookPages == null && isError) {
                    Icon(Icons.Default.Warning, contentDescription = "Error")
                }
            },
            supportingText = {
                if (bookPages == null && isError) {
                    Text(text = "Please enter a number of pages")
                }
            },
            label = { Text("Number of Pages") },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
        // Input field for book date with date picker and manual input
        OutlinedTextField(
            value = bookDate,
            onValueChange = {
                bookDate = it
                isDateError = !bookListViewModel.isValidDate(it)
            },
            isError = isDateError || (bookDate.isEmpty() && isError),
            trailingIcon = {
                if ((bookDate.isEmpty() && isError) || isDateError) {
                    IconButton(onClick = {datePickerDialog = true}) {
                        Icon(Icons.Default.Warning, contentDescription = "Error")
                    }
                }
                else
                {
                    IconButton(onClick = { datePickerDialog = true }) {
                        Icon(Icons.Filled.DateRange, contentDescription = "Date")
                    }
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
            ShowDatePicker(onDateSelected = { selectedDate ->
                bookDate = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))},
                onDismiss = { datePickerDialog = false })
        }
        // Save changes button
        Button(onClick = {
            // Update Book in database
            if (bookTitle.isEmpty() || bookAuthor.isEmpty() || bookGenre.isEmpty() ||
                bookProgress == null || bookPages == null || bookDate.isEmpty() || isDateError
                || bookProgress!! > bookPages!!
            ) {
                isError = true
                return@Button
            }
            val book = Book(bookTitle = bookTitle,
                bookAuthor = bookAuthor,
                bookGenre = bookGenre,
                datePublished = bookDate,
                dateAdded = currentBook!!.dateAdded,
                pages = bookPages!!,
                progress = bookProgress!!,
                id = currentBook.id)
            coroutineScope.launch {
                bookListViewModel.updateBook(book)
            }
            onSaveChanges()
        }, colors = ButtonDefaults.buttonColors(Color(0xFF6650a4)))
        {
            Text(text = "Save Changes", color = Color.White)
        }
    }
}