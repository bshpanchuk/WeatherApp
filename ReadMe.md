
# Test Weather App

## Table of Contents

- [Task requirements](#task-requirements)
- [Architecture](#architecture)
- [Libraries](#libraries)
- [Build](#build)
- [Demo](#demo)

## Task requirements
[Requirements](https://github.com/mondoktamas/AndroidTestAppRequirements/blob/main/README.md)

## Architecture

MVVM + MVI-ish

## Libraries

Libraries used in the whole application are:

- [Kotlin 2.0](https://kotlinlang.org/docs/whatsnew20.html#0)
- [Jetpack](https://developer.android.com/jetpack)🚀
    - [Viewmodel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manage UI related data in a lifecycle conscious way
      and act as a channel between use cases and ui
    - [Room](https://developer.android.com/jetpack/androidx/releases/room) -  provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite
- [Jetpack Compose](https://developer.android.com/compose) - is Android’s recommended modern toolkit for building native UI
- [Places SDK](https://developers.google.com/maps/documentation/places/android-sdk/versions) - The autocomplete service returns place predictions in response to user search queries.
- [KSP](https://github.com/google/ksp) - an API that you can use to develop lightweight compiler plugins
- [Ktor](https://github.com/ktorio/ktor) - type safe http client and supports coroutines out of the box
- [Kotlin Serialisation](https://github.com/Kotlin/kotlinx.serialization) - a compiler plugin, that generates visitor code for serializable classes, runtime library with core serialization API and support libraries with various serialization formats
- [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) - Library Support for coroutines
- [Material 3 Design](https://m3.material.io/) - build awesome beautiful UIs
- [Hilt](https://dagger.dev/hilt/) - a standard way to incorporate Dagger dependency injection into an Android application

## Build

- Create `secrets.properties` file:

  PLACES_API_KEY=PLACES_API_KEY  
  OPEN_WEATHER_API_KEY=OPEN_WEATHER_API_KEY

- Place your `key.jks` in the `keystore` folder
- Create `keystore_config.gradle.dsl` file in the `keystore` folder.

## Demo

|<img src="demo/img1.jpg" width=200/>|<img src="demo/img2.jpg" width=200/>|<img src="demo/img3.jpg" width=200/>|
[Video]()