package com.bshpanchuk.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bshpanchuk.data.local.db.table.PlaceRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: PlaceRoom)

    @Query("DELETE FROM PlaceRoom WHERE label = :place")
    suspend fun removeFavoriteById(place: String)

    @Query("SELECT * FROM PlaceRoom")
    fun getAllFavorites(): Flow<List<PlaceRoom>>

    @Query("SELECT * FROM PlaceRoom WHERE id = :placeId")
    suspend fun getPlaceById(placeId: Long): PlaceRoom?
}