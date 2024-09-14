package com.bshpanchuk.data.repository

import com.bshpanchuk.data.local.LocationProvider
import com.bshpanchuk.data.places.AutocompleteProvider
import com.bshpanchuk.domain.model.weather.Location
import com.bshpanchuk.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationProvider: LocationProvider,
    private val autocompleteProvider: AutocompleteProvider,
) : LocationRepository {

    override suspend fun getLocation(): Location? {
        return locationProvider.getLocation()
    }

    override suspend fun getLocationFromAddress(address: String): Location? {
        return locationProvider.getCoordinatesFromAddress(address)
    }

    override suspend fun getAddressFromLocation(location: Location): String? {
        return locationProvider.getAddressFromLocation(location)
    }

    override suspend fun getAddressAutocomplete(query: String, origin: Location?): List<String> {
        return autocompleteProvider.getAddressSuggestions(query, origin)
    }
}