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
import androidx.compose.runtime.remember
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
    var isChecked by rememberSaveable { mutableStateOf(false) }

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
            Text(
                text = "Title: ${book.bookTitle}\n" +
                        "Author: ${book.bookAuthor}\n" +
                        "Publish Date: ${book.datePublished}",
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Pages Complete : ${book.progress}/${book.pages}",
                color = Color.LightGray,
                style = TextStyle(lineHeight = 20.sp, fontSize = 13.2.sp,
                    fontFamily = Serif)
            )
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
            Checkbox(isChecked, onCheckedChange = {
                isChecked = it
                bookListViewModel.isChecked = isChecked
                CoroutineScope(Dispatchers.IO).launch {
                    bookListViewModel.setCustomListById(book.id)
                }
            },
                modifier = Modifier.padding(start = 290.dp),
                colors = CheckboxDefaults.colors(Color(0xFF1f960f), Color.White, Color.White))
        }
    }
    if(bookListViewModel.success.value)
    {
        isChecked = false
    }
}
