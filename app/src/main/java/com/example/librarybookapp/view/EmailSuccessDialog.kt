package com.example.librarybookapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.librarybookapp.viewmodel.BookListViewModel

@Composable
fun EmailSuccessDialog(onDismiss: () -> Unit, bookListViewModel: BookListViewModel, onConfirm: () -> Unit)
{
    Dialog(onDismissRequest = onDismiss,
        content ={
            Column(modifier = Modifier.padding(25.dp).background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Email Sent", color = Color(0xFF6650a4),
                    style = typography.titleLarge,)
                Text("Email has been sent to ${bookListViewModel.email.value}",
                    color = Color(0xFF6650a4),
                    style = typography.labelLarge,
                    modifier = Modifier.padding(top = 10.dp,start = 10.dp, end = 10.dp))

                Spacer(modifier = Modifier.padding(10.dp))

                Button(onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(Color(0xFF6650a4))) {
                    Text(text = "Ok", color = Color.White)
                }
            }
        })
}