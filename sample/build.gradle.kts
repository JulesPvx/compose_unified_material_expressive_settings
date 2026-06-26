/*
 * '||''''|                                                  ||                       .|'''.|            .     .    ||
 *  ||  .    ... ... ... ...  ... ..    ....   ....   ....  ...  .... ...   ....      ||..  '    ....  .||.  .||.  ...  .. ...     ... .  ....
 *  ||''|     '|..'   ||'  ||  ||' '' .|...|| ||. '  ||. '   ||   '|.  |  .|...||      ''|||.  .|...||  ||    ||    ||   ||  ||   || ||  ||. '
 *  ||         .|.    ||    |  ||     ||      . '|.. . '|..  ||    '|.|   ||         .     '|| ||       ||    ||    ||   ||  ||    |''   . '|..
 * .||.....| .|  ||.  ||...'  .||.     '|...' |'..|' |'..|' .||.    '|     '|...'    |'....|'   '|...'  '|.'  '|.' .||. .||. ||.  '||||. |'..|'
 *                    ||                                                                                                         .|....'
 *                   ''''
 *
 * Copyright (c) 2026 Jules Pouvreaux
 *
 * /home/paeelluu/StudioProjects/compose_unified_material_expressive_settings/sample/build.gradle.kts
 *
 * This file is part of the Compose Unified Material Expressive Settings Library.
 * Licensed under the Proprietary License.
 * All rights reserved.
 */

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "fr.paeelluu.composeunifiedsettingsui"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "fr.paeelluu.composeunifiedsettingsui"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        version = libs.versions.libraryVersion.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":compose-settings-ui"))
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.compose.material.icons.extended.android)
    implementation(libs.androidx.compose.ui.tooling.preview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.compose.ui.tooling)
}