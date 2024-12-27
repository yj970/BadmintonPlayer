// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    // val objectboxVersion by extra("4.0.3") // For KTS build scripts

    repositories {
        mavenCentral()
    }

    dependencies {
        // Android Gradle Plugin 4.1.0 or later supported
        classpath("io.objectbox:objectbox-gradle-plugin:4.0.3")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}