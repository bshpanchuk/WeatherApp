package com.bshpanchuk.data.places

import android.util.Log
import com.bshpanchuk.domain.model.weather.Location
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AutocompleteProvider @Inject constructor(
    private val placesClient: PlacesClient
) {

    suspend fun getAddressSuggestions(query: String, origin: Location?) = suspendCoroutine { cont ->
        val request =
            FindAutocompletePredictionsRequest.builder()
                .setOrigin(origin?.let { LatLng(it.lat, it.lon) })
                .setTypesFilter(listOf(PlaceTypes.CITIES))
                .setQuery(query)
                .build()
        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                cont.resume(
                    response.autocompletePredictions
                        .map {
                            it.getFullText(null).toString()
                        }
                )
            }
            .addOnFailureListener { error ->
                Log.e("AutocompleteProvider", error.message.toString())
            }
    }
}