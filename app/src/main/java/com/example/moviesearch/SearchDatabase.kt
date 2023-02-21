package com.example.moviesearch

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Search::class], version = 1)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun getSearchDao(): SearchDAO

    companion object {
        private var INSTANCE: SearchDatabase? = null

        fun getDatabase(context: Context) : SearchDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    SearchDatabase::class.java, "search_database"
                ).build()
                // for in-memory database
                /*INSTANCE = Room.inMemoryDatabaseBuilder(
                    context, MyDatabase::class.java
                ).build()*/
            }
            return INSTANCE as SearchDatabase
        }
    }
}