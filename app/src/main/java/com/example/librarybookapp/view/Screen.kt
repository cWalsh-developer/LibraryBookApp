package com.example.librarybookapp.view

sealed class Screen(val route: String, val title: String) {
    object BookList : Screen("bookList", "Book List")
}