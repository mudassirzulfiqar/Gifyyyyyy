plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

dependencies {

    val lintVersion = "31.1.2"
    compileOnly("com.android.tools.lint:lint-api:$lintVersion")
    testImplementation("com.android.tools.lint:lint-tests:$lintVersion")
}

tasks.withType<Jar> {
    enabled = true
    manifest {
        attributes["Lint-Registry"] = "com.moodi.IssueRegister"
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
