package com.book.darts

import androidx.lifecycle.LiveData

class DartRepository(private val bookDao: BookDao, private val id: Int) {
    val dartsList: LiveData<List<Dart>> = bookDao.getDartsList(id)

    suspend fun insert(dart: Dart) {
        bookDao.insert(dart)
    }

    suspend fun deleteBook(id: Int) {
        bookDao.deleteBook(id)
    }
}