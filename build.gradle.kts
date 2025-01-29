plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.buildConfig) apply false
    alias(libs.plugins.sqlDelight) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.googleGmsGoogleServices) apply false
    alias(libs.plugins.crashlytics) apply false
}