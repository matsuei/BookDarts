package com.example.bookdarts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao {

    @Query("SELECT * from book_table ORDER BY created_at ASC")
    fun getBooksByCreatedAt(): LiveData<List<Book>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Book)

    @Query("DELETE FROM book_table")
    suspend fun deleteAll()
}