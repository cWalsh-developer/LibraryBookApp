package com.example.librarybookapp.model

import androidx.room.Database

@Database(entities = [Book::class], version = 1)
abstract class AppDatabase {
}