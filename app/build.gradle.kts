@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.roborazzi) apply false
    alias(libs.plugins.secrets) apply false
    id("jacoco")
    id("kotlin-kapt")
}

android {
    namespace = "com.android.yamba"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.android.yamba"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("benchmark") {
            initWith(buildTypes.getByName("release"))
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += listOf("release")
            isDebuggable = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material)
    implementation(libs.androidx.foundation.layout)
    implementation(libs.androidx.material.icons)
    implementation(libs.androidx.animation)
    implementation(libs.accompanist.swipe.refresh)
    implementation(libs.accompanist.insets)
    implementation(libs.accompanist.system.ui)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.retrofit.squareup)
    implementation(libs.retrofit.converter)
    implementation(libs.okhttp3.squareup)
    implementation(libs.okhttp3.squareup.logging)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.lifecycle.java)
    implementation(libs.androidx.window)
    implementation(libs.androidx.palette)
    implementation(libs.androidx.datestore)
    implementation(libs.kotlinx.datetime)
    implementation(libs.coil.compose)
    implementation(libs.google.gson)
    implementation(libs.hilt.ext.compiler)
    implementation(libs.hilt.ext.work)
    coreLibraryDesugaring(libs.jdk.lib)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}