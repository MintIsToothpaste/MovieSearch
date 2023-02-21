package com.example.moviesearch

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_table")
data class Search(
    @PrimaryKey @ColumnInfo val movieName: String
)