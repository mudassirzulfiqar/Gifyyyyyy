import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.moodi.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val key: String = gradleLocalProperties(rootDir).getProperty("api-key") ?: ""
        if (key.isEmpty()) {
            throw Exception("Please add api-key in local.properties file")
        }
        buildConfigField("String", "API_KEY", "\"$key\"")

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
        buildConfig = true
    }
}

dependencies {

    implementation(project(mapOf("path" to ":domain")))
    // adding network
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitlogging)
    implementation(Dependencies.retrofitGson)
    // adding unit tests
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.coreKtx)
    testImplementation(Dependencies.Test.extJunit)
    testImplementation(Dependencies.Test.mockServer)
    testImplementation(Dependencies.Test.turbine)

    // adding coroutine tests
    testImplementation(Dependencies.Test.coroutineTest)
    testImplementation(Dependencies.Test.coreTest)

    // adding android components
    implementation(Dependencies.core)
    implementation(Dependencies.lifeCycleExtension)

}