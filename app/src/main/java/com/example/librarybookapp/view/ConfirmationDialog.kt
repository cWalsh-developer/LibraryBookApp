package com.example.librarybookapp.view



import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ConfirmationDialog(onDismiss: () -> Unit, onConfirm: () -> Unit)
{
    //Confirmation dialog for deleting a book
    AlertDialog(onDismissRequest = onDismiss,
        title = { Text(text = "Confirm Delete") },
        text = { Text(text = "Are you sure you want to delete this book?") },
        confirmButton = {
            Button(onClick = onConfirm, colors = ButtonDefaults.buttonColors(Color(0xFF6650a4))) {
                Text(text = "Yes", color = Color.White)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(Color(0xFF6650a4))) {
                Text(text = "No", color = Color.White)
            }
        })
}