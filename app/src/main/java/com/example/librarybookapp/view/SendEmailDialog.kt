package com.example.librarybookapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily.Companion.Monospace
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration.Companion.Underline
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.librarybookapp.viewmodel.BookListViewModel

@Composable
fun SendEmailDialog(onDismiss: () -> Unit, bookListViewModel: BookListViewModel, onConfirm: () -> Unit)
{
    var emailAddress by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    Dialog(onDismissRequest = onDismiss,
        content ={
            Column(modifier = Modifier.padding(5.dp).background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = "Send Book List to Email", color = Color(0xFF6650a4),
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    style = typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Monospace,
                    textDecoration = Underline)

                Text(text = "Enter Recipient Email Address",
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 10.dp),
                    style = typography.labelLarge)
                TextField(value = emailAddress, onValueChange = {emailAddress = it},
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    label = {Text("Email")})

                Spacer(modifier = Modifier.padding(16.dp))

                Text(text = "Enter Recipient Name",
                    color = Color.DarkGray,
                    style = typography.labelLarge)
                TextField(value = name, onValueChange = {name = it},
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    label = {Text("Name")})

                Spacer(modifier = Modifier.padding(16.dp))

                Button(onClick = {bookListViewModel.sendEmail(emailAddress, name)},
                    colors = ButtonDefaults.buttonColors(Color(0xFF6650a4))) {
                    if (bookListViewModel.success.value)
                    {
                        CircularProgressIndicator(color = Color.White)
                        onConfirm()
                        onDismiss()
                    }
                    else
                    {
                        Text(text = "Send", color = Color.White)
                    }
                }
            }
        })
}