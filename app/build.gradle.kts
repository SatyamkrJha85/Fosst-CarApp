plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    alias(libs.plugins.google.gms.google.services)

}

android {
    namespace = "com.theapplicationpad.fosst_carapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.theapplicationpad.fosst_carapp"
        minSdk = 26
        targetSdk = 34
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.compose.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    //lottie
    implementation( "com.airbnb.android:lottie-compose:5.2.0")


    //pager
    implementation ("com.google.accompanist:accompanist-pager:0.12.0")

    // Ui Controller

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.32.0")

    // Icon
    implementation ("androidx.compose.material:material-icons-extended:1.6.7")

    implementation ("androidx.datastore:datastore-preferences:1.0.0")


    implementation("androidx.activity:activity-ktx:1.5.1")

    val room_version = "2.6.1"

    implementation ("androidx.room:room-runtime:$room_version")
    annotationProcessor ("androidx.room:room-compiler:$room_version")

// To use Kotlin annotation processing tool (kapt)
    kapt ("androidx.room:room-compiler:$room_version")

// Kotlin extensions for Room coroutines support
    implementation ("androidx.room:room-ktx:$room_version")

    // LiveData
    implementation( "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")


    implementation("androidx.compose.runtime:runtime-livedata:1.6.4")

    // Coil
    implementation("io.coil-kt:coil-compose:2.3.0")

    // Constraint Layout
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.0")

    // Razor Pay

    implementation ("com.razorpay:checkout:1.6.33")

    // Ar View
    implementation ("io.github.sceneview:arsceneview:0.10.0")

    // Google Maps Compose
    implementation("com.google.maps.android:maps-compose:1.0.0")

    // Google Play services for Maps
    implementation("com.google.android.gms:play-services-maps:18.0.2")

}