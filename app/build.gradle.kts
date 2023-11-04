import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
    kotlin("kapt")
}
apply(from = "$rootDir/jacoco.gradle")

// // Jacoco Configuration
// val jacocoReport by tasks.registering(JacocoReport::class) {
//    group = "Quality"
//    description = "Reports code coverage on tests within the Wire Android codebase"
//    val buildVariant = "debug" // It's not necessary to run unit tests on every variant so we default to "devDebug"
//    dependsOn("test${buildVariant.capitalize()}UnitTest")
//
//    val outputDir = "$buildDir/jacoco/html"
//    val classPathBuildVariant = buildVariant
//
//    reports {
//        xml.required.set(true)
//        html.required.set(true)
//        html.outputLocation.set(file(outputDir))
//    }
//
//    classDirectories.setFrom(
//        fileTree(project.buildDir) {
//            include(
//                "**/classes/**/main/**", // This probably can be removed
//                "**/tmp/kotlin-classes/$classPathBuildVariant/**"
//            )
//            exclude(
//                "**/R.class",
//                "**/R\$*.class",
//                "**/BuildConfig.*",
//                "**/Manifest*.*",
//                "**/Manifest$*.class",
//                "**/*Test*.*",
//                "**/Injector.*",
//                "android/**/*.*",
//                "**/*\$Lambda$*.*",
//                "**/*\$inlined$*.*",
//                "**/di/*.*",
//                "**/*Database.*",
//                "**/*Response.*",
//                "**/*Application.*",
//                "**/*Entity.*",
//                "**/mock/**",
//                "**/*Screen*", // These are composable classes
//                "**/*Kt*", // These are "usually" kotlin generated classes
//                "**/theme/**/*.*", // Ignores jetpack compose theme related code
//                "**/common/**/*.*", // Ignores jetpack compose common components related code
//                "**/navigation/**/*.*" // Ignores jetpack navigation related code
//            )
//        }
//    )
//
//    sourceDirectories.setFrom(
//        fileTree(project.projectDir) {
//            include("src/main/java/**", "src/main/kotlin/**")
//        }
//    )
//
//    executionData.setFrom(
//        fileTree(project.buildDir) {
//            include("**/*.exec", "**/*.ec")
//        }
//    )
//
//    doLast { println("Report file: $outputDir/index.html") }
// }

/*tasks.register("testCoverage") {
    group = "Quality"
    description = "Reports code coverage on tests within the Wire Android codebase."
    dependsOn(jacocoReport)
}*/

/*jacoco {
    toolVersion = "0.8.9"
}*/
ktlint {
    android.set(true)
    outputColorName.set("RED")
    reporters {
        reporter(ReporterType.HTML)
    }
    filter {
        exclude("**/generated/**")
        exclude("**/build/**")
    }
}

android {
    /*   lint {
           baseline = file("lint-baseline.xml")
       }*/

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
    testImplementation(project(mapOf("path" to ":common")))

    // Android UI Testing Dependencies
    androidTestImplementation(Dependencies.Test.extJunit)
    androidTestImplementation(Dependencies.Test.espresso)
    androidTestImplementation(Dependencies.Test.coroutineTest)
    androidTestImplementation(Dependencies.Test.coreTest)
//    androidTestImplementation(Dependencies.Test.composeUI)
    androidTestImplementation(Dependencies.Test.hiltTesting)
    androidTestAnnotationProcessor(Dependencies.Test.hiltCompilerTesting)

    testAnnotationProcessor(Dependencies.Test.hiltCompilerTesting)
    kaptAndroidTest(Dependencies.Test.hiltCompilerTesting)
    kaptTest(Dependencies.Test.hiltCompilerTesting)
}
