package com.example.librarybookapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily.Companion.Serif
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.librarybookapp.model.Book
import com.example.librarybookapp.viewmodel.BookListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun BookCard(
    book: Book,
    onInfo: () -> Unit,
    bookListViewModel: BookListViewModel,
    onProgress: () -> Float
) {
    //remember saveable is used to save the state of the checkbox across configuration changes
    var isChecked by rememberSaveable { mutableStateOf(false) }
// Card for displaying basic book information
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                bookListViewModel.setBookInfo(book)
                onInfo()
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF6650a4))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color(0xFF6650a4)),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            //Book title, author, and publish date displayed using \n for formatting
            Text(
                text = "Title: ${book.bookTitle}\n" +
                        "Author: ${book.bookAuthor}\n" +
                        "Publish Date: ${book.datePublished}",
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            //Progress display of pages read and total pages
            Text(
                text = "Pages Complete : ${book.progress}/${book.pages}",
                color = Color.LightGray,
                style = TextStyle(lineHeight = 20.sp, fontSize = 13.2.sp,
                    fontFamily = Serif)
            )
            //Linear progress bar for book progress with percentage in a row for formatting
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(onProgress, color = Color.Green, modifier = Modifier.weight(1f))
                Text(
                    text = bookListViewModel.calculateProgressPercentage(book),
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            //Checkbox for adding book to custom email list arranged with a row for formatting
            Row(modifier= Modifier.fillMaxWidth().padding(start = 125.dp), horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically)
            {
                Text("Add to Custom Email List", color = Color.White,
                    modifier = Modifier.clickable { isChecked = !isChecked },
                    style = TextStyle(fontSize = 13.2.sp))
                Checkbox(isChecked, onCheckedChange = {
                    isChecked = it
                    bookListViewModel.isChecked = isChecked
                    CoroutineScope(Dispatchers.IO).launch {
                        bookListViewModel.setCustomListById(book.id)
                    }
                },
                    colors = CheckboxDefaults.colors(Color(0xFF1f960f), Color.White, Color.White))
            }
        }
    }
    //Removing checks once the email list has been sent successfully
    if(bookListViewModel.success.value)
    {
        isChecked = false
    }
}
