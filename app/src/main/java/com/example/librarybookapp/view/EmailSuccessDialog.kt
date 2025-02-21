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
import com.example.librarybookapp.viewmodel.BookListViewModel

@Composable
fun EmailSuccessDialog(onDismiss: () -> Unit, bookListViewModel: BookListViewModel, onConfirm: () -> Unit)
{
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
                        text = "Email Sent",
                        color = Color(0xFF6650a4),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold)

                    Text("Email sent to ${bookListViewModel.email.value}",
                        color = Color.DarkGray,
                        style = MaterialTheme.typography.labelLarge)

                    Text("Please allow up to 2 minutes for email to arrive",
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