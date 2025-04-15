plugins {
    id("com.android.application") version("8.8.0") apply(false)
    id("org.jetbrains.kotlin.android") version("1.9.24") apply(false)
    id("com.google.dagger.hilt.android") version("2.51.1") apply(false)
    id("androidx.navigation.safeargs.kotlin") version("2.7.7") apply(false)
}

buildscript {
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}