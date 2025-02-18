package com.example.librarybookapp.view

sealed class Screen(val route: String, val title: String) {
    object BookList : Screen("bookList", "Book List")
    object AddBook : Screen("addBook", "Add Book")
    object BookInfo : Screen("bookInfo", "Book Info")
    object EditBook : Screen("editBook", "Edit Book")

}