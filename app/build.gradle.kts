import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {


    lint{
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

        testInstrumentationRunner = "com.moodi.task.util.HiltTestRunner"
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
        viewBinding = true
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

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui-graphics")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    lintChecks(project(":linter"))

    // adding android components
    implementation(Dependencies.core)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraint)
    implementation(Dependencies.lifeCycleExtension)
    implementation(Dependencies.lifeCycleViewModelKtx)
    implementation(Dependencies.fragmentKtx)
    debugImplementation(Dependencies.Test.fragmentTesting)

    // adding network
    implementation(Dependencies.retrofit)
    implementation(Dependencies.core)
    implementation(Dependencies.retrofitGson)
    implementation(Dependencies.retrofitlogging)

    // adding logging
    implementation(Dependencies.timber)

    // adding di with tests
    implementation(Dependencies.hilt)
    testImplementation(Dependencies.Test.hiltTesting)
    kapt(Dependencies.hiltCompiler)
    androidTestImplementation(Dependencies.Test.hiltTesting)
    kaptTest(Dependencies.Test.hiltCompilerTesting)
    testAnnotationProcessor(Dependencies.Test.hiltCompilerTesting)
    kaptAndroidTest(Dependencies.Test.hiltCompilerTesting)
    androidTestAnnotationProcessor(Dependencies.Test.hiltCompilerTesting)


    // adding glide
    implementation(Dependencies.glide)

    // adding unit tests
    testImplementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.coreKtx)
    testImplementation(Dependencies.Test.extJunit)

    // adding coroutine tests
    testImplementation(Dependencies.Test.coroutineTest)
    testImplementation(Dependencies.Test.coreTest)

    // adding mockito tests
    testImplementation(Dependencies.Test.kotlinMock)
    testImplementation(Dependencies.Test.mockk)

    // adding turbine to test flows
    testImplementation(Dependencies.Test.turbine)
    testImplementation(Dependencies.Test.mockServer)

    // adding android tests for UIs
    androidTestImplementation(Dependencies.Test.extJunit)
    androidTestImplementation(Dependencies.Test.espresso)
    androidTestImplementation(Dependencies.Test.coroutineTest)
    androidTestImplementation(Dependencies.Test.coreTest)
    implementation(Dependencies.Test.espressoContrib)

    val composeVersion = "1.1.2"

    // Material Design 3
    implementation("androidx.compose.material3:material3:$composeVersion")
    // or Material Design 2
    implementation("androidx.compose.material:material:$composeVersion")
    // or skip Material Design and build directly on top of foundational components
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    // or only import the main APIs for the underlying toolkit systems,
    // such as input and measurement/layout
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation ("androidx.navigation:navigation-compose:2.6.0")
    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.material:material-icons-core:$composeVersion")
    // Optional - Add full set of material icons
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("io.coil-kt:coil:2.4.0")
    implementation("io.coil-kt:coil-gif:2.4.0")
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")

}