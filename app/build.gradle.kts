plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
}

android {
    namespace = "com.example.painting"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.painting"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    // Verziók
    val room_version = "2.6.1"
    val nav_version = "2.9.0"

    dependencies {
        // --- AndroidX alapok ---
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)

        // --- Jetpack Compose ---
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)

        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)

        // --- Navigation ---
        implementation("androidx.navigation:navigation-compose:$nav_version")

        // --- Hilt ---
        implementation(libs.hilt.android)
        kapt(libs.hilt.android.compiler)
        implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

        // --- Room ---
        implementation("androidx.room:room-runtime:$room_version")
        implementation(libs.androidx.room.common)
        kapt("androidx.room:room-compiler:$room_version")
        implementation("androidx.room:room-ktx:$room_version") // opcionális, de hasznos

        // --- Retrofit + Moshi ---
        implementation("com.squareup.retrofit2:retrofit:2.11.0")
        implementation("com.squareup.moshi:moshi:1.15.2")
        implementation("com.squareup.retrofit2:converter-moshi:2.4.0")

        implementation("com.squareup.retrofit2:converter-gson:2.11.0")

        // --- Tesztelés ---
        testImplementation(libs.junit)
        testImplementation("io.mockk:mockk:1.14.2")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6")
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)

    }
    testImplementation(kotlin("test"))

}

kapt {
    correctErrorTypes = true
}