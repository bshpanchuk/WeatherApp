package com.bshpanchuk.weatherapp.di

import com.bshpanchuk.domain.repository.WeatherRepository
import com.bshpanchuk.domain.usecase.api.GetWeatherWithForecastUseCase
import com.bshpanchuk.domain.usecase.impl.GetWeatherWithForecastUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetWeatherUseCase(weatherRepository: WeatherRepository): GetWeatherWithForecastUseCase {
        return GetWeatherWithForecastUseCaseImpl(weatherRepository)
    }

}