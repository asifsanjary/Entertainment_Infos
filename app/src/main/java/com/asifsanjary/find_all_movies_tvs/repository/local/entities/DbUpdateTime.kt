package com.asifsanjary.find_all_movies_tvs.repository.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dbUpdateTime")
data class DbUpdateTime (
    @PrimaryKey @ColumnInfo(name = "id") val uid: Int,
    @ColumnInfo(name = "timestamp") val timestamp: Long
)