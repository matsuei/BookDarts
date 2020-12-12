package com.book.darts

import androidx.lifecycle.LiveData

class BookRepository(private val bookDao: BookDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<Book>> = bookDao.getBooksByCreatedAt()

    suspend fun insert(book: Book) {
        bookDao.insert(book)
    }
}