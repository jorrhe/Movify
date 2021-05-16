package com.movify.repositorios

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.movify.database.*

class ListRepository(application: Application) {

    private val movieListDao: MovieListDao? = MovieRoomDatabase.getDatabase(application)?.movieDao()

    suspend fun getAlLists():List<MovieList>?{
            return movieListDao?.getAllLists()
    }

    suspend fun getListById(id:Int): ListWithMovies? {
        return movieListDao?.getListById(id)
    }

}