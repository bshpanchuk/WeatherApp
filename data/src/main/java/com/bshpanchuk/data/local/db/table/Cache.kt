package com.bshpanchuk.data.local.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cache")
data class Cache(
    @PrimaryKey val id: Long,
    val json: String,
    val timestamp: Long
)