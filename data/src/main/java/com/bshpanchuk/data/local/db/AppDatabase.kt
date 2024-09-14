package com.bshpanchuk.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bshpanchuk.data.local.db.dao.CacheDao
import com.bshpanchuk.data.local.db.dao.PlaceDao
import com.bshpanchuk.data.local.db.table.Cache
import com.bshpanchuk.data.local.db.table.PlaceRoom

@Database(
    entities = [PlaceRoom::class, Cache::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao
    abstract fun cacheDao(): CacheDao

}