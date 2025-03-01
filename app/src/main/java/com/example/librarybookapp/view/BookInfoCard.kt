package com.example.librarybookapp.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BookInfoCard(bookInfo:String)
{
    //Card for displaying book information from passed parameter
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF6650a4)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    )
    {
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp)){
            Text(text = bookInfo, color = Color.White)
        }
    }
}