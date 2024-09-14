package com.bshpanchuk.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bshpanchuk.data.local.db.table.Cache

@Dao
interface CacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCache(dataCache: Cache)

    @Query("SELECT * FROM cache WHERE id = :id")
    suspend fun getCache(id: Long): Cache?
}