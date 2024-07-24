plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.nytimes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nytimes"
        minSdk = 24
        targetSdk = 30
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.testng)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.20")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")

    // Mockito for mocking
    testImplementation (libs.mockito.core)
    testImplementation (libs.mockito.inline)

    // Coroutines testing
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")

    // AndroidX Test - Instrumentation testing
    androidTestImplementation ("androidx.test.ext:junit:1.2.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")

    // Core library
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("androidx.recyclerview:recyclerview:1.2.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.4.0")
    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.4.0")

    // AndroidX Test - Core library
    androidTestImplementation ("androidx.test:core:1.4.0")

    // AndroidX Test - Rules
    androidTestImplementation ("androidx.test:rules:1.4.0")

    // AndroidX Test - Runner
    androidTestImplementation ("androidx.test:runner:1.4.0")
}