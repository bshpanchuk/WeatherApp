package com.bshpanchuk.domain.repository

import com.bshpanchuk.domain.model.Place
import com.bshpanchuk.domain.model.weather.WeatherResult
import kotlinx.coroutines.flow.Flow

interface PlacesRepository {

    suspend fun addPlaceToFavorites(place: Place)

    suspend fun deletePlace(place: String)

    fun observePlaces(): Flow<List<Place>>

    suspend fun saveCache(weather: WeatherResult)

    suspend fun getCache(): WeatherResult?

}