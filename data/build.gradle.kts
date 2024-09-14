plugins {
    id("com.android.library")
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.serialization)
    alias(libs.plugins.android.secrets)
}

android {
    namespace = "com.bshpanchuk.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    secrets {
        propertiesFileName = "secrets.properties"

        defaultPropertiesFileName = "default.secrets.properties"
    }
}

dependencies {
    implementation(project(":domain"))

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.ktor)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.gms.location)
    implementation(libs.gms.places)

    implementation(libs.android.room)
    implementation(libs.android.room.ktx)
    ksp(libs.android.room.ksp)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}