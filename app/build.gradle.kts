plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.hairbookfront"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hairbookfront"
        minSdk = 25
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


    implementation(Dependencies.coreKts)
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiToolingPreview)
    implementation(Dependencies.composeMaterial3)
    implementation(Dependencies.navigationCompose)
    implementation(Dependencies.composeUiTooling)
    implementation(Dependencies.composeUiTestManifest)
    implementation(Dependencies.lifecycleViewmodelKtx)
    androidTestImplementation(platform(Dependencies.composeBom))
    androidTestImplementation(Dependencies.composeTestJunit4)

    //Dagger Hilt
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltCompiler)
    kapt(Dependencies.hiltAndroidCompiler)
    implementation(Dependencies.hiltNavigationCompose)

    //Retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.okhttp3)
    implementation(Dependencies.gsonConverter)
    implementation(Dependencies.moshi)

    implementation(Dependencies.moshiConverter)
    implementation(Dependencies.loggingInterceptor)

    //Coroutines
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)
    //SplashScreen
    implementation(Dependencies.splashScreen)

    //Coil
    implementation(Dependencies.coil)

    //Datastore
    implementation(Dependencies.dataStore)

    //Paging
    implementation(Dependencies.paging)
    implementation(Dependencies.pagingCompose)

    //Accompanist
    implementation(Dependencies.accompanist)

    //Room
    implementation(Dependencies.room)
    kapt(Dependencies.roomCompiler)
    implementation(project(Modules.utilities))

    //Timber
    implementation(Dependencies.timber)

    //Lifecycle compose
    implementation(Dependencies.lifecycleViewmodelCompose)
    implementation(Dependencies.lifecycleRuntimeCompose)

    //Datastore
    implementation(Dependencies.datastore)
    implementation(Dependencies.datastorePreferences)
    implementation(Dependencies.json)

    implementation(Dependencies.maxkeppelerSheets)

    // CALENDAR
    implementation(Dependencies.maxkeppelerSheetsCalender)

    // CLOCK
    implementation(Dependencies.maxkeppelerSheetsClock)


}

kapt {
    correctErrorTypes = true
}