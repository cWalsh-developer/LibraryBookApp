package com.example.librarybookapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.librarybookapp.model.Book

@Composable
fun ExistDialog(onDismiss: () -> Unit, existingBook: Book,
                onConfirm: () -> Unit)
{
    //Dialog for when a book already exists in the library
    Dialog(onDismissRequest = onDismiss,
        content ={
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Add Book Error",
                        color = Color(0xFF6650a4),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold)

                    Text("${existingBook.bookTitle} by ${existingBook.bookAuthor} Already Exists in Library",
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.labelLarge)

                    Button(onClick = onConfirm, colors = ButtonDefaults.buttonColors(Color(0xFF6650a4)))
                    {
                        Text(text = "OK", color = Color.White)
                    }
                }
            }
        })
}