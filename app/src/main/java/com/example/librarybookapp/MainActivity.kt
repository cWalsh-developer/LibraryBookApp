package com.example.librarybookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.librarybookapp.model.DatabaseInstance
import com.example.librarybookapp.ui.theme.LibraryBookAppTheme
import com.example.librarybookapp.view.NavigationGraph
import com.example.librarybookapp.viewmodel.BookListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val bookListViewModel = BookListViewModel(DatabaseInstance.getDatabase(this))

            LibraryBookAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavigationGraph(
                        navController = navController,
                        bookListViewModel = bookListViewModel)
                }
            }
        }
    }
}