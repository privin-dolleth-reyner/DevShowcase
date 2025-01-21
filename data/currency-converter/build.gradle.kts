import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
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

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "dataCurrencyConverter"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "dataCurrencyConverter.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets{
        val desktopMain by getting

        desktopMain.dependencies {
            api(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)
        }

        commonMain.dependencies {
            api(projects.domain.currencyConverter)
            implementation(libs.bundles.ktor)
            implementation(libs.koin.core)

            api(libs.cmp.settings)
            api(libs.kotlinx.coroutines.core)
        }

        nativeMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        androidMain.dependencies {
            api(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
        }
        wasmWasiMain.dependencies {
            implementation(libs.ktor.client.wasm.js)
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}