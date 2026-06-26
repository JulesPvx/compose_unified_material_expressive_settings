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
 * /home/paeelluu/StudioProjects/compose_unified_material_expressive_settings/compose-settings-ui/build.gradle.kts
 *
 * This file is part of the Compose Unified Material Expressive Settings Library.
 * Licensed under the Proprietary License.
 * All rights reserved.
 */

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    id("com.vanniktech.maven.publish")
}

android {
    namespace = "fr.paeelluu.compose_settings_ui"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 24
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
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.compose.material.icons.extended.android)
    implementation(libs.colorpicker.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

mavenPublishing {
    coordinates("io.github.julespvx", "compose-settings-ui", libs.versions.libraryVersion.get())

    pom {
        name.set("Compose Settings UI")
        description.set("A simple UI library for Jetpack Compose settings screens.")
        url.set("https://github.com/JulesPvx/compose-settings-ui")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("JulesPvx")
                name.set("JulesPvx")
            }
        }
        scm {
            connection.set("scm:git:github.com/JulesPvx/compose-settings-ui.git")
            developerConnection.set("scm:git:ssh://github.com/JulesPvx/compose-settings-ui.git")
            url.set("https://github.com/JulesPvx/compose-settings-ui")
        }
    }

    publishToMavenCentral()

    signAllPublications()
}