buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath(libs.gradle)
        classpath(libs.firebase.crashlytics.gradle)
        classpath ("com.android.tools.build:gradle:8.1.0")
        classpath ("com.google.gms:google-services:4.4.2")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.firebase.crashlytics") version "3.0.2" apply false
}