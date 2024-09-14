package com.bshpanchuk.data.local.di

import android.content.Context
import androidx.room.Room
import com.bshpanchuk.data.local.db.AppDatabase
import com.bshpanchuk.data.local.db.dao.CacheDao
import com.bshpanchuk.data.local.db.dao.PlaceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "WeatherAppDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePlaceDao(appDb: AppDatabase): PlaceDao {
        return appDb.placeDao()
    }

    @Provides
    fun provideCacheDao(appDb: AppDatabase): CacheDao {
        return appDb.cacheDao()
    }

}