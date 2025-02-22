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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.librarybookapp.R
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
    var isSorted by remember { mutableStateOf(false) }

    val filteredBooks by remember{
        derivedStateOf{
            if (searchQuery.isBlank())
            {
                books
            } else
            {
                books.filter { it.bookGenre.contains(searchQuery, ignoreCase = true) ||
                        bookListViewModel.calculateProgressPercentage(it).contains(searchQuery,
                            ignoreCase = true) || it.progress.toString().contains(searchQuery,
                            ignoreCase = true)}
            }
        }
    }

    val sortedBooks by remember {
        derivedStateOf {
            if (isSorted) {
                filteredBooks.sortedBy { it.bookTitle }
            } else {
                filteredBooks
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
            label = { Text("Search by genre or reading progress", fontSize = 14.5.sp) },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            trailingIcon = {
                IconButton(onClick = {isSorted = !isSorted}) {
                    Icon(painter = painterResource(id = R.drawable.ic_sort),
                        contentDescription = "Filter")}
                },
            shape = RoundedCornerShape(topStart = 30.dp, bottomStart = 30.dp, topEnd = 30.dp,
                bottomEnd = 30.dp)
        )

        Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = SpaceEvenly)
        {
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
                enabled = !books.isEmpty(),
                colors = ButtonDefaults.buttonColors(Color(0xFF6650a4))) {
                Row {
                    Icon(Icons.Default.Email, contentDescription = "Email",
                        tint = Color.White)
                    Text(text = "Email List", color = Color.White,
                        modifier = Modifier.padding(start = 10.dp))
                }
            }
        }

        if (showDialog)
        {
            SendEmailDialog(onDismiss = { showDialog = false }, bookListViewModel = bookListViewModel,
                onConfirm = {changeDialog = true})
        }
        if (changeDialog)
        {
            EmailSuccessDialog(onDismiss = {changeDialog = false}, bookListViewModel = bookListViewModel,
                onConfirm = {changeDialog = false}, "Email Sent")
        }
        LazyColumn(modifier = Modifier.fillMaxWidth())
        {
            items(sortedBooks)
            {
                book ->
                BookCard(book = book, onInfo = {onNavigateToBookInfo()},
                    bookListViewModel = bookListViewModel,
                    onProgress = {bookListViewModel.calculateProgress(book)})
            }
        }
    }
}