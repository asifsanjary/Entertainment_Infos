package com.asifsanjary.find_all_movies_tvs

import android.app.Application
import com.asifsanjary.find_all_movies_tvs.repository.Repository
import com.asifsanjary.find_all_movies_tvs.repository.local.AppDatabase
import com.asifsanjary.find_all_movies_tvs.repository.local.LocalRepository
import com.asifsanjary.find_all_movies_tvs.repository.network.NetworkRepository

class FindAllMoviesTvApplication : Application() {
    private val appDb by lazy { AppDatabase.getDatabase(this) }
    private val networkRepository by lazy { NetworkRepository() }
    private val localRepository by lazy { LocalRepository(appDb.moviesDao()) }
    val repository by lazy { Repository(localRepository, networkRepository) }
}