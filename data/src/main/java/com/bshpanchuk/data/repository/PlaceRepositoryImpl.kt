package com.bshpanchuk.data.repository

import android.util.Log
import com.bshpanchuk.data.local.db.dao.CacheDao
import com.bshpanchuk.data.local.db.dao.PlaceDao
import com.bshpanchuk.data.local.db.table.Cache
import com.bshpanchuk.data.mapper.toDb
import com.bshpanchuk.data.mapper.toDomain
import com.bshpanchuk.domain.model.Place
import com.bshpanchuk.domain.model.weather.WeatherResult
import com.bshpanchuk.domain.repository.PlacesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeDao: PlaceDao,
    private val cacheDao: CacheDao,
): PlacesRepository {
    override suspend fun addPlaceToFavorites(place: Place) {
        placeDao.insertFavorite(place.toDb())
    }

    override suspend fun deletePlace(place: String) {
        placeDao.removeFavoriteById(place)
    }

    override fun observePlaces(): Flow<List<Place>> {
        return placeDao.getAllFavorites().map { it.map { it.toDomain() } }
    }

    override suspend fun saveCache(weather: WeatherResult) {
        Log.e("SAVE", "0")
        val json = Json.encodeToString(weather)
        val cache = Cache(
            json = json,
            timestamp = System.currentTimeMillis(),
            id = -1
        )
        Log.e("SAVE", "1")
        cacheDao.insertCache(cache)
        Log.e("SAVE", json)
    }

    override suspend fun getCache(): WeatherResult? {
        return cacheDao.getCache(-1)?.let {
            Log.e("Load", it.json)
            Json.decodeFromString<WeatherResult>(it.json)
        }
    }
}