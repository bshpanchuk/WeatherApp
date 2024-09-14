package com.bshpanchuk.domain.repository

import com.bshpanchuk.domain.model.weather.Location

interface LocationRepository {

    suspend fun getLocation(): Location?

    suspend fun getLocationFromAddress(address: String): Location?

    suspend fun getAddressFromLocation(location: Location): String?

    suspend fun getAddressAutocomplete(query: String, origin: Location?): List<String>
}