object App {
    const val compileSdk = 33
    const val minSdk = 24
    const val targetSdk = 33
    const val applicationId = "com.moodi.task"
    const val namespace = "com.moodi.task"
    const val buildToolsVersion = "31.0.0"
    const val jvmTarget = "1.8"
    const val kotlinCompilerExtensionVersion = "1.5.31"
    const val kotlinVersion = "1.5.31"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"

}

object AppVersion {
    const val minSdk = App.minSdk
    const val major = 0
    const val minor = 0
    const val patch = 1
}


object Versions {
    const val core = "1.8.0"
    const val material = "1.5.0"
    const val constraint = "2.1.4"
    const val appCompat = "1.6.1"
    const val espresso = "3.5.1"
    const val junit = "4.13.2"
    const val testJUnit = "1.1.5"
    const val espressoCore = "3.5.1"
    const val coreKtx = "1.3.0"
    const val mockitoCore = "3.5.13"
    const val turbine = "1.0.0"
    const val kotlinMock = "2.2.0"
    const val coroutineTest = "1.7.2"
    const val hilt = "2.46.1"
    const val timber = "5.0.1"
    const val mockServer = "4.9.3"
    const val retrofit = "2.9.0"
    const val retrofitLogging = "4.10.0"
    const val lifeCycleExtension = "2.2.0"
    const val lifeCycleViewModelKtx = "2.4.1"
    const val coreTest = "2.0.0"
    const val glide = "4.12.0"
    const val fragmentKtx = "1.6.1"
    const val espressoContrib = "3.5.1"
    const val fragmentTesting = "1.6.1"
    const val mockk = "1.13.7"
    const val hiltTesting = "2.44"
    const val lifecycle = "2.6.2"
    const val compose = "1.7.2"
    const val composeBom = "2023.03.00"
    const val composeVersion = "1.1.2"
    const val navigationCompose = "2.6.0"
    const val coil = "2.4.0"
    const val navigationComposeFragment = "2.3.5"
    const val navigationFragmentKtx = "1.0.0"


}

object Dependencies {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofitlogging =
        "com.squareup.okhttp3:logging-interceptor:${Versions.retrofitLogging}"
    const val lifeCycleExtension =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifeCycleExtension}"
    const val lifeCycleViewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycleViewModelKtx}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val compose = "androidx.activity:activity-compose:${Versions.compose}"
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val composeUIGraphics = "androidx.compose.ui:ui-graphics"
    const val material3 = "androidx.compose.material3:material3:${Versions.composeVersion}"
    const val foundation = "androidx.compose.foundation:foundation:${Versions.composeVersion}"
    const val composeUI = "androidx.compose.ui:ui:${Versions.composeVersion}"
    const val navigationCompose =
        "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
    const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}"
    const val materialIcon =
        "androidx.compose.material:material-icons-core:${Versions.composeVersion}"
    const val materialIconExtended =
        "androidx.compose.material:material-icons-extended:${Versions.composeVersion}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coilGif = "io.coil-kt:coil-gif:${Versions.coil}"
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coil}"
    const val navigationComposeFragment =
        "androidx.hilt:hilt-navigation-compose:${Versions.navigationFragmentKtx}"
    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationComposeFragment}"


    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val extJunit = "androidx.test.ext:junit:${Versions.testJUnit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
        const val coreKtx = "androidx.test:core-ktx:${Versions.coreKtx}"
        const val mockitoCore = "org.mockito:mockito-core:${Versions.coreKtx}"
        const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoCore}"
        const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"
        const val kotlinMock = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.kotlinMock}"
        const val mockServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockServer}"
        const val coreTest = "androidx.arch.core:core-testing:${Versions.coreTest}"
        const val coroutineTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineTest}"
        const val espressoContrib =
            "androidx.test.espresso:espresso-contrib:${Versions.espressoContrib}"
        const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragmentTesting}"
        const val mockk = "io.mockk:mockk:${Versions.mockk}"
        const val hiltTesting = "com.google.dagger:hilt-android-testing:${Versions.hiltTesting}"
        const val hiltCompilerTesting =
            "com.google.dagger:hilt-android-compiler:${Versions.hiltTesting}"
        const val composeUI = "androidx.compose.ui:ui-test-junit4"
        const val composeTestManifest = "androidx.compose.ui:ui-test-manifest"


    }

}