package com.example.librarybookapp.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
}