package com.example.librarybookapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.librarybookapp.ui.theme.LibraryBookAppTheme
import com.example.librarybookapp.viewmodel.BookListViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.librarybookapp.model.DatabaseInstance
import com.example.librarybookapp.view.AddBookScreen
import com.example.librarybookapp.view.BookInfoScreen
import com.example.librarybookapp.view.BookListScreen
import com.example.librarybookapp.view.EditBookScreen
import com.example.librarybookapp.view.Screen
import com.example.librarybookapp.viewmodel.BookInfoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val bookListViewModel = BookListViewModel(DatabaseInstance.getDatabase(this))

            LibraryBookAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding
                    ->
                    NavigationGraph(
                        navController = navController,
                        bookListViewModel = bookListViewModel,
                        modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController, bookListViewModel: BookListViewModel,
    modifier: Modifier
)
{
    NavHost(navController = navController, startDestination = Screen.BookList.route)
    {
        composable(Screen.BookList.route)
        {
            BookListScreen(bookListViewModel = bookListViewModel,
                onNavigateToAddScreen = {navController.navigate(Screen.AddBook.route)},
                onNavigateToBookInfo = {navController.navigate(Screen.BookInfo.route)})
        }
        composable(Screen.AddBook.route)
        {
            AddBookScreen(onBookAdded = {navController.navigate(Screen.BookList.route)},
                bookListViewModel = bookListViewModel)

        }
        composable(Screen.BookInfo.route)
        {
            BookInfoScreen(bookListViewModel,
                onDelete = {navController.navigate(Screen.BookList.route)},
                onEdit = {navController.navigate(Screen.EditBook.route)})
        }
        composable(Screen.EditBook.route)
        {
            EditBookScreen(bookListViewModel, onSaveChanges = {navController.navigate(Screen.BookList.route)})
        }
    }
}