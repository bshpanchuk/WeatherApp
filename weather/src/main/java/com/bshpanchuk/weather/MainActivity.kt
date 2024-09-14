package com.bshpanchuk.weather

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.bshpanchuk.presentation.mvi.HandleEvent
import com.bshpanchuk.presentation.theme.WeatherAppTheme
import com.bshpanchuk.weather.screen.MainViewModel
import com.bshpanchuk.weather.screen.home.HomeCallback
import com.bshpanchuk.weather.screen.home.HomeUiEvent
import com.bshpanchuk.weather.screen.home.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                ) { _ ->
                    val viewModel: MainViewModel by viewModels()
                    val state by viewModel.uiState

                    val locationPermissionLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { isGranted ->
                            viewModel.handleUiEvent(
                                HomeUiEvent.OnPermissionGrantedState(
                                    isGranted
                                )
                            )
                        }
                    )

                    val locationRequestLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == RESULT_OK) {
                                viewModel.handleUiEvent(HomeUiEvent.RequestLocation)
                            }
                        }
                    )

                    HandleEvent(channel = viewModel.callbackChannel) { event ->
                        when (event) {
                            is HomeCallback.RequestEnableLocationCallback -> {
                                locationRequestLauncher.launch(
                                    IntentSenderRequest.Builder(
                                        event.exception.resolution
                                    ).build()
                                )
                            }

                            HomeCallback.RequestLocationPermission -> {
                                locationPermissionLauncher.launch(
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            }

                            HomeCallback.LocationPermissionDenied -> {
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        getString(R.string.request_location_permission),
                                        getString(R.string.settings),
                                        duration = SnackbarDuration.Long
                                    )

                                    if (result == ActionPerformed) {
                                        Intent(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", packageName, null)
                                        ).also(::startActivity)
                                    }
                                }
                            }

                            HomeCallback.SomethingWentWrong -> {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        getString(R.string.error),
                                    )
                                }
                            }
                        }
                    }

                    MainScreen(
                        state = state,
                        uiEvent = viewModel::handleUiEvent,
                        requestPermission = {
                            locationPermissionLauncher.launch(
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        }
                    )
                }
            }
        }
    }
}