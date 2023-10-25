import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
    kotlin("kapt")
}

android {

    ktlint {
        android.set(true)
        outputColorName.set("RED")
    }

    lint {
        baseline = file("lint-baseline.xml")
    }

    namespace = App.namespace
    compileSdk = App.compileSdk

    defaultConfig {
        applicationId = App.applicationId
        minSdk = App.minSdk
        targetSdk = App.targetSdk
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        debug {
            isDebuggable = true
        }
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }

    sourceSets {
        getByName("debug") {
            java {
                srcDirs("src/debug/java")
            }
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures {
        compose = true
    }
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}
dependencies {

    // Project Modules
    implementation(project(mapOf("path" to ":domain")))
    implementation(project(mapOf("path" to ":data")))
    implementation(project(mapOf("path" to ":common")))
    lintChecks(project(":linter"))

    // AndroidX Libraries
    implementation(Dependencies.core)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.constraint)
    implementation(Dependencies.lifeCycleExtension)
    implementation(Dependencies.lifeCycleViewModelKtx)
    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.material3)
    implementation(Dependencies.foundation)

    // Jetpack Compose Dependencies
    implementation(Dependencies.composeUI)
    implementation(Dependencies.navigationCompose)
    implementation(Dependencies.toolingPreview)
    implementation(Dependencies.materialIcon)
    implementation(Dependencies.materialIconExtended)
    implementation(Dependencies.coil)
    implementation(Dependencies.coilGif)
    implementation(Dependencies.coilCompose)
    implementation(Dependencies.navigationComposeFragment)
    implementation(Dependencies.navigationFragmentKtx)
    implementation(Dependencies.lifecycle)
    implementation(Dependencies.compose)
    implementation(Dependencies.composeBom)
    implementation(Dependencies.composeUIGraphics)

    // Network and Image Loading
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitGson)
    implementation(Dependencies.retrofitlogging)
    implementation(Dependencies.glide)
    implementation(Dependencies.coil)

    // Logging and Testing
    implementation(Dependencies.timber)

    // Dependency Injection
    implementation(Dependencies.hilt)
    kapt(Dependencies.hiltCompiler)

    // Debug Dependencies
    debugImplementation(Dependencies.toolingPreview)
    debugImplementation(Dependencies.Test.composeTestManifest)
    debugImplementation(Dependencies.Test.fragmentTesting)

    // Testing Dependencies
    testImplementation(Dependencies.Test.hiltTesting)
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.coreKtx)
    testImplementation(Dependencies.Test.extJunit)
    testImplementation(Dependencies.Test.coroutineTest)
    testImplementation(Dependencies.Test.coreTest)
    testImplementation(Dependencies.Test.kotlinMock)
    testImplementation(Dependencies.Test.mockk)
    testImplementation(Dependencies.Test.turbine)
    testImplementation(Dependencies.Test.mockServer)

    // Android UI Testing Dependencies
    androidTestImplementation(Dependencies.Test.extJunit)
    androidTestImplementation(Dependencies.Test.espresso)
    androidTestImplementation(Dependencies.Test.coroutineTest)
    androidTestImplementation(Dependencies.Test.coreTest)
    androidTestImplementation(Dependencies.Test.composeUI)
    androidTestImplementation(Dependencies.Test.hiltTesting)
    androidTestAnnotationProcessor(Dependencies.Test.hiltCompilerTesting)

    testAnnotationProcessor(Dependencies.Test.hiltCompilerTesting)
    kaptAndroidTest(Dependencies.Test.hiltCompilerTesting)
    kaptTest(Dependencies.Test.hiltCompilerTesting)
}
