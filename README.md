# Compose Settings UI

A simple, generic Jetpack Compose library for creating grouped settings screens. It automatically handles the corner radius logic to create visually distinct groups of settings items.

## Installation

First, add JitPack to your project's repositories in your root settings.gradle.kts or build.gradle.kts file:

```Kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

Next, add the dependency to your app module's build.gradle.kts file:

```Kotlin
dependencies {
    implementation("com.github.JulesPvx:compose_unified_material_expressive_settings:1.0.0")
}
```

## Usage

Use the SettingsSection composable to group your items. The library provides a DSL to easily add clickable actions, toggle switches, or custom generic items. It relies entirely on standard Material 3 components and themes.

```Kotlin
import fr.paeelluu.composesettings.SettingsSection
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun SettingsScreen() {
    Column {
        SettingsSection(title = "Account Settings") {
            action(
                title = "Profile",
                subtitle = "Manage your public profile",
                onClick = { /* Handle click */ }
            )
            
            switch(
                title = "Enable Notifications",
                checked = true,
                onCheckedChange = { isChecked -> /* Handle toggle */ }
            )
        }
    }
}
```

### License

This project is open-source and available under the MIT License.
