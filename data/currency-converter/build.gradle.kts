import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.sqlDelight)
}

val secretKeyProperties by lazy {
    val secretKeyPropertiesFile = rootProject.file("secrets.properties")
    Properties().apply { secretKeyPropertiesFile.inputStream().use { secret -> load(secret) } }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "dataCurrencyConverter"
            isStatic = true
        }
    }

    sourceSets{
        commonMain.dependencies {
            api(projects.domain.currencyConverter)
            api(libs.kotlinx.datetime)
            implementation(libs.bundles.ktor)
            api(libs.koin.core)

            api(libs.cmp.settings)
            api(libs.kotlinx.coroutines.core)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.native)
        }

        androidMain.dependencies {
            api(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)

            implementation(libs.sqldelight.android)
        }
    }
}

android {
    namespace = "com.cmp.showcase.data.currency.converter"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

buildConfig {
    packageName = "com.cmp.showcase.data.currency.converter"
    useKotlinOutput { internalVisibility = true }
    buildConfigField(
        "String",
        "API_KEY",
        "${secretKeyProperties["API_KEY"]}"
    )
    buildConfigField(
        "String",
        "APP_NAME",
        "\"${rootProject.name}\""
    )
}

sqldelight{
    databases{
        create("CurrencyConverter"){
            packageName.set("com.cmp.showcase.currency.converter")
        }
    }
}