plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}
apply(from = "$rootDir/jacoco.gradle")

android {
    namespace = "com.moodi.domain"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(Dependencies.core)
    implementation(Dependencies.appCompat)

    testImplementation(Dependencies.Test.extJunit)
    testImplementation(Dependencies.Test.mockk)

    androidTestImplementation(Dependencies.Test.espresso)
}
