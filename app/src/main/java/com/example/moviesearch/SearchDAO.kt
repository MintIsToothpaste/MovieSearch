package com.example.moviesearch

import androidx.room.*

@Dao
interface SearchDAO {
    @Query("SELECT * FROM search_table")
    fun getAll(): List<Search>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(search: Search)

    @Delete
    fun delete(search: Search)

    @Update
    fun update(search: Search)
}