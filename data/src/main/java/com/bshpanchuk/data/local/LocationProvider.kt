package com.bshpanchuk.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Looper
import com.bshpanchuk.domain.model.weather.Location
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalCoroutinesApi::class)
class LocationProvider @Inject constructor(@ApplicationContext private val context: Context) {

    private val geocoder = Geocoder(context)

    private val locationRequest = LocationRequest
        .Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, REPORT_INTERVAL)
        .setMinUpdateIntervalMillis(FASTEST_REPORT_INTERVAL)
        .build()

    @Throws(Exception::class)
    suspend fun getLocation(): Location? = coroutineScope {
        suspendCancellableCoroutine { cont ->
            val settings =
                LocationServices.getSettingsClient(context)
            settings.checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest)
                    .build()
            ).addOnSuccessListener {
                launch {
                    val location = requestLastLocation() ?: requestCurrentLocation()
                    cont.resume(location, onCancellation = null)
                }
            }.addOnFailureListener(cont::resumeWithException)
        }
    }

    suspend fun getCoordinatesFromAddress(address: String): Location? =
        suspendCancellableCoroutine { continuation ->
            runCatching {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocationName(address, 1) { addresses ->
                        val result = addresses
                            .firstOrNull()
                            ?.let {
                                Location(it.latitude, it.longitude)
                            }
                        continuation.resume(result)
                    }
                } else {
                    @Suppress("DEPRECATION")
                    val result = geocoder.getFromLocationName(address, 1)
                        ?.firstOrNull()
                        ?.let {
                            Location(it.latitude, it.longitude)
                        }
                    continuation.resume(result)
                }
            }.onFailure {
                continuation.resume(null)
            }
        }

    suspend fun getAddressFromLocation(location: Location): String? =
        suspendCancellableCoroutine { continuation ->

            fun List<Address>?.getFormattedAddress(): String {
                val address = this?.firstOrNull()
                return if (address?.locality.isNullOrEmpty().not()) {
                    "${address?.locality}, ${address?.countryName}"
                } else {
                    address?.getAddressLine(0)
                }.orEmpty()
            }

            runCatching {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(location.lat, location.lon, 1) { addresses ->
                        continuation.resume(addresses.getFormattedAddress())
                    }
                } else {
                    @Suppress("DEPRECATION")
                    val addresses =
                        geocoder.getFromLocation(location.lat, location.lon, 1)
                    continuation.resume(addresses.getFormattedAddress())
                }

            }.onFailure { continuation.resume(null) }
        }

    @SuppressLint("MissingPermission")
    private suspend fun requestLastLocation(): Location? = suspendCancellableCoroutine { cont ->
        val client =
            LocationServices.getFusedLocationProviderClient(context)
        client.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                cont.resume(
                    Location(location.latitude, location.longitude),
                    onCancellation = null
                )
            } else {
                cont.resume(null, onCancellation = null)
            }
        }.addOnFailureListener(cont::resumeWithException)
    }

    @SuppressLint("MissingPermission")
    private suspend fun requestCurrentLocation(): Location? = suspendCancellableCoroutine { cont ->
        val client =
            LocationServices.getFusedLocationProviderClient(context)

        val listener = LocationListener {
            cont.resume(
                Location(it.latitude, it.longitude),
                onCancellation = null
            )
        }
        client.requestLocationUpdates(
            locationRequest,
            listener,
            Looper.myLooper()
        )

        cont.invokeOnCancellation {
            client.removeLocationUpdates(listener)
        }

    }

    companion object {

        private const val REPORT_INTERVAL = 1000L
        private const val FASTEST_REPORT_INTERVAL = 500L

    }

}