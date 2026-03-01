plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.gnexus.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.gnexus.app"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_25
        targetCompatibility = JavaVersion.VERSION_25
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {
    // --- CORE & LIFECYCLE ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // --- COMPOSE ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.valentinilk.shimmer.compose)

    // --- COMPOSE - ADAPTIVE & WINDOW SIZE ---
    implementation(libs.androidx.compose.material3.adaptive.navigation.suite)
    implementation(libs.androidx.compose.material3.window.size)
    implementation(libs.androidx.compose.adaptive.layout)
    implementation(libs.androidx.compose.adaptive.navigation)
    implementation(libs.androidx.adaptive.layout)

    // --- IMAGE LOADING ---
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // --- NETWORK ---
    implementation(libs.bundles.network)

    // --- JSON PARSING (MOSHI) ---
    implementation(libs.bundles.moshi)
    ksp(libs.squareup.moshi.kotlin.codegen)

    // --- lifecycle Viewmodel ---
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // --- Hilt - Dagger ---
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.lifecycle.viewmodel)
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // --- DATABASE (ROOM) ---
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.paging.compose)

    // =======================================================================
    //                                 TESTING
    // =======================================================================

    // --- LOCAL UNIT TESTS (JVM) ---
    testImplementation(libs.bundles.test.unit)
    testImplementation(libs.squareup.mock)

    // --- INSTRUMENTED TESTS (Android Device/Emulator) ---
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.bundles.test.android)
    androidTestImplementation(libs.androidx.room.test)

    // =======================================================================
    //                                 DEBUG
    // ======================================================================

    // --- COMPOSE TOOLING (PREVIEWS & INSPECTION) ---
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
