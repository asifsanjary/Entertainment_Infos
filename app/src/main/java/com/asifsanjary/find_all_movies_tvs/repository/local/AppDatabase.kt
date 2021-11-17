package com.asifsanjary.find_all_movies_tvs.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.asifsanjary.find_all_movies_tvs.repository.local.entities.*
import kotlinx.coroutines.CoroutineScope

@Database(entities = [MovieBasic::class, TopRatedMovies::class, PopularMovies::class, UpcomingMovies::class, NowPlayingMovies::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "s_tmdb_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}