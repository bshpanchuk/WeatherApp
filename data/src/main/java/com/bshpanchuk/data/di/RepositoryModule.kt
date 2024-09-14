package com.bshpanchuk.data.di

import com.bshpanchuk.data.repository.LocationRepositoryImpl
import com.bshpanchuk.data.repository.PlaceRepositoryImpl
import com.bshpanchuk.data.repository.WeatherRepositoryImpl
import com.bshpanchuk.domain.repository.LocationRepository
import com.bshpanchuk.domain.repository.PlacesRepository
import com.bshpanchuk.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindWeatherRepository(repository: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    fun bindLocationRepository(repository: LocationRepositoryImpl): LocationRepository

    @Binds
    @Singleton
    fun bindLPlacesRepository(repository: PlaceRepositoryImpl): PlacesRepository

}