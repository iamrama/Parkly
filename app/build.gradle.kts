plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.com.google.dagger.hilt)
}

fun getProperty(key: String): String = project.findProperty(key)?.toString() ?: ""


android {
    namespace = "com.rk.parkly"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.rk.parkly"
        minSdk = 24
        targetSdk = 36
        versionCode = 2
        versionName = "1.1"
        val apiKey = project.findProperty("NEWS_API")?.toString() ?: ""

        buildConfigField(
            "String",
            "API_KEY",
            apiKey
        )
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
        viewBinding = true
        buildConfig = true
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation(libs.google.dagger.hilt.android)
    ksp(libs.google.dagger.hilt.compiler)
    implementation(libs.google.hilt.navigation.compose)
    implementation(libs.animation.compose)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.retrofit.mockwebserver)

    implementation(libs.room)
    implementation("androidx.room:room-ktx:2.7.0")
    ksp(libs.room.compiler)
    implementation(libs.room.paging)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)


    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)
    implementation(libs.datastore.preferences)

}