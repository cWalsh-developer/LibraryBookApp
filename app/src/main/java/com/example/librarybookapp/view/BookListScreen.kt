package com.example.librarybookapp.view

import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceEvenly
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.librarybookapp.model.Book
import com.example.librarybookapp.viewmodel.BookListViewModel

@Composable
fun BookListScreen(bookListViewModel: BookListViewModel,
                   onNavigateToAddScreen: () -> Unit,
                   onNavigateToBookInfo: () -> Unit)
{
    var searchQuery by remember { mutableStateOf("") }
    val books = remember { mutableStateListOf<Book>() }
    var showDialog by remember { mutableStateOf(false) }
    var changeDialog by remember { mutableStateOf(false) }

    val filteredBooks by remember {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                books
            } else {
                books.filter { it.bookTitle.contains(searchQuery, ignoreCase = true) ||
                        it.bookAuthor.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    LaunchedEffect(Unit) {
        books.addAll(bookListViewModel.getBooks())
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            shape = RoundedCornerShape(topStart = 30.dp, bottomStart = 30.dp,
                topEnd = 30.dp, bottomEnd = 30.dp)
        )
        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = SpaceEvenly) {
            Button(onClick ={onNavigateToAddScreen()},
                colors = ButtonDefaults.buttonColors(Color(0xFF6650a4))) {
                Row {
                    Icon(Icons.Default.Add, contentDescription = "Add",
                        tint = Color.White)
                    Text(text = "Add Book", color = Color.White,
                        modifier = Modifier.padding(start = 10.dp))
                }
            }
            Button(onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(Color(0xFF6650a4))) {
                Row {
                    Icon(Icons.Default.Email, contentDescription = "Email",
                        tint = Color.White)
                    Text(text = "Email List", color = Color.White,
                        modifier = Modifier.padding(start = 10.dp))
                }
            }
        }
        if (showDialog) {
            SendEmailDialog(onDismiss = { showDialog = false }, bookListViewModel = bookListViewModel,
                onConfirm = {changeDialog = true})
        }
        if (changeDialog)
        {
            EmailSuccessDialog(onDismiss = {changeDialog = false}, bookListViewModel = bookListViewModel,
                onConfirm = {changeDialog = false})
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(filteredBooks)
            {
                book ->
                BookCard(book = book, onInfo = {onNavigateToBookInfo()}, bookListViewModel = bookListViewModel, onProgress = {bookListViewModel.calculateProgress(book)})
            }
        }
    }


}