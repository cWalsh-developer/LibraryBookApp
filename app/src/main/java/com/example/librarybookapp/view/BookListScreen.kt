package com.example.librarybookapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.librarybookapp.model.Book
import com.example.librarybookapp.viewmodel.BookListViewModel
import kotlinx.coroutines.launch

@Composable
fun BookListScreen(bookListViewModel: BookListViewModel,
                   onNavigateToAddScreen: () -> Unit,
                   onNavigateToBookInfo: (bookInfo: Book) -> Unit)
{
    val books = remember { mutableListOf<Book>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            books.addAll(bookListViewModel.getBooks())
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick ={onNavigateToAddScreen()}) {
            Text(text = "Add Book")
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(books)
            {
                book ->
                BookCard(book = book, onInfo = {onNavigateToBookInfo(book)})
            }
        }
    }


}