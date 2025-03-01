package com.example.librarybookapp.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.librarybookapp.viewmodel.BookListViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController, bookListViewModel: BookListViewModel
)
{
    //Navigation graph for the app navigation
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
            AddBookScreen(onBookAdded = {navController.popBackStack()},
                bookListViewModel = bookListViewModel)

        }
        composable(Screen.BookInfo.route)
        {
            BookInfoScreen(bookListViewModel,
                onDelete = {navController.popBackStack()},
                onEdit = {navController.navigate(Screen.EditBook.route)})
        }
        composable(Screen.EditBook.route)
        {
            EditBookScreen(bookListViewModel, onSaveChanges = {
                navController.navigate(Screen.BookList.route)
                {
                    popUpTo(0){inclusive = true}
                }
            })
        }
    }
}