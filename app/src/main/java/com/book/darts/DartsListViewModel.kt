package com.book.darts

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DartsListViewModel(application: Application, id: Int) : AndroidViewModel(application) {
    private val repository: DartRepository
    val dartsList: LiveData<List<Dart>>
    val bookId: Int

    init {
        val bookDao = BookRoomDatabase.getDatabase(application, viewModelScope).bookDao()
        repository = DartRepository(bookDao, id)
        bookId = id
        dartsList = repository.dartsList
    }

    fun insert(dart: Dart) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(dart)
    }

    fun deleteBook() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteBook(bookId)
    }
}

class DartsListViewModelFactory(private val application: Application, private val id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DartsListViewModel(application, id) as T
    }
}