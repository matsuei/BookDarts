package com.example.bookdarts

import androidx.lifecycle.LiveData

class BookRepository(private val bookDao: BookDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<Book>> = bookDao.getTitleAlphabetizedBooks()

    suspend fun insert(book: Book) {
        bookDao.insert(book)
    }
}