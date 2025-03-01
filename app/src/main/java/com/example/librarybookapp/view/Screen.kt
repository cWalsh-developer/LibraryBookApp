package com.example.librarybookapp.view

//Screen objects for navigation
sealed class Screen(val route: String) {
    object BookList : Screen("bookList")
    object AddBook : Screen("addBook")
    object BookInfo : Screen("bookInfo")
    object EditBook : Screen("editBook")

}