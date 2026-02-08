plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.gnexus.app"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.gnexus.app"
        minSdk = 33
        targetSdk = 36
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
   kotlin {
       compilerOptions {
           jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
       }
   }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // --- CORE & LIFECYCLE ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // --- COMPOSE ---
    // The BOM (Bill of Materials) manages versions for all Compose libraries.
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    // Image loading
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // --- COMPOSE - ADAPTIVE & WINDOW SIZE ---
    // The navigation suite provides adaptive layouts like NavigationSuiteScaffold.
    implementation(libs.androidx.compose.material3.adaptive.navigation.suite)
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)
    // For calculating window size classes (Compact, Medium, Expanded).
    implementation(libs.androidx.compose.material3.window.size)

    // --- COMPOSE - TOOLING & PREVIEW ---
    // These are needed for previews in Android Studio.
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // --- DB(Room) ---
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    implementation(libs.androidx.room.guava)

    // --- API ---
    // Retrofit + OkHttp
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.moshi)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.okhttp.logging)
    // Moshi
    implementation(libs.squareup.moshi)
    implementation(libs.squareup.moshi.kotlin)
    implementation(libs.squareup.moshi.kotlin.codegen)
    // Mockserver
    implementation(libs.squareup.mock)

    // --- TESTING ---
    // Unit Testing
    testImplementation(libs.junit)
    // Instrumentation Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Use the Compose BOM for testing artifacts as well.
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    // DB(room) Test
    implementation(libs.androidx.room.test)
}