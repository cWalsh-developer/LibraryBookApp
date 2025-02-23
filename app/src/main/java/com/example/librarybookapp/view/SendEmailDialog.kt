package com.example.librarybookapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.librarybookapp.viewmodel.BookListViewModel

@Composable
fun SendEmailDialog(
    onDismiss: () -> Unit,
    bookListViewModel: BookListViewModel,
    onConfirm: () -> Unit,
) {
    var emailAddress by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    val loading by bookListViewModel.isLoading
    val success by bookListViewModel.success

    Dialog(onDismissRequest = onDismiss) {
    val focusManager = LocalFocusManager.current
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
                    text = "Send Book List to Email",
                    color = Color(0xFF6650a4),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    textDecoration = TextDecoration.Underline,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Enter Recipient Email Address",
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.labelLarge
                )
                TextField(
                    value = emailAddress,
                    onValueChange = { emailAddress = it
                        bookListViewModel.resetCredentials()},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF6650a4),
                        unfocusedContainerColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        focusedLabelColor = Color.White,
                        cursorColor = Color.White,
                        unfocusedLabelColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = {focusManager.moveFocus(FocusDirection.Down)}),
                    isError = bookListViewModel.isSubmitted.value &&
                            bookListViewModel.isEmailEmpty.value ||
                            bookListViewModel.isEmailValid.value,
                    supportingText = {
                        if(bookListViewModel.isSubmitted.value)
                        {
                            when{
                                bookListViewModel.isEmailEmpty.value ->
                                    Text(
                                        text = "Please enter an email address",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                bookListViewModel.isEmailValid.value ->
                                    Text(
                                        text = "Please enter a valid email address",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                            }
                        }
                    },
                    trailingIcon = {
                        if (bookListViewModel.isSubmitted.value
                            && bookListViewModel.isEmailEmpty.value ||
                            bookListViewModel.isEmailValid.value)
                        {
                            Icon(Icons.Filled.Warning, contentDescription = "Error")
                        }
                    }
                )
                Text(
                    text = "Enter Recipient Name",
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.labelLarge
                )
                TextField(
                    value = name,
                    onValueChange = { name = it
                        bookListViewModel.resetCredentials() },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Name") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF6650a4),
                        unfocusedContainerColor = Color.LightGray,
                        disabledContainerColor = Color.LightGray,
                        focusedLabelColor = Color.White,
                        cursorColor = Color.White,
                        unfocusedLabelColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = {bookListViewModel.sendEmail(emailAddress.trim(),
                        name.trim())}),
                    isError = bookListViewModel.isSubmitted.value && bookListViewModel.isNameEmpty.value,
                    supportingText = {
                        if(bookListViewModel.isSubmitted.value && bookListViewModel.isNameEmpty.value)
                        {
                            Text(
                                text = "Please enter a name",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    },
                    trailingIcon = {
                        if (bookListViewModel.isSubmitted.value
                            && bookListViewModel.isNameEmpty.value) {
                            Icon(Icons.Filled.Warning, contentDescription = "Error")
                        }
                    }
                )

                Button(
                    onClick = {
                        bookListViewModel.sendEmail(emailAddress.trim(), name.trim())
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFF6650a4)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (loading) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text(text = "Send", color = Color.White)
                    }
                    if (success) {
                        onConfirm()
                        onDismiss()
                        bookListViewModel.isChecked = false
                        bookListViewModel.resetCredentials()
                    }
                }
            }
        }
    }
}