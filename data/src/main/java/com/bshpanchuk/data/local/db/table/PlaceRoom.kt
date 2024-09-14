package com.bshpanchuk.data.local.db.table

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["label"], unique = true)])
data class PlaceRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val lat: Double,
    val lon: Double,
    val label: String,
)