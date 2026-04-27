# Compose Settings UI

A minimalist Jetpack Compose library for building grouped settings screens. It dynamically calculates corner radii to create visually distinct groups of settings items, automatically inheriting your app's Material 3 theme.

## Installation

Add JitPack to your project's repository list, typically found in `settings.gradle.kts`:

```Kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

```

### Using Version Catalogs (Recommended)

Declare the dependency in your `gradle/libs.versions.toml` file:

```Kotlin
[versions]
composeSettings = "1.0.0"

[libraries]
compose-settings = { module = "com.github.JulesPvx:compose_unified_material_expressive_settings", version.ref = "composeSettings" }

```

Then, add it to your app-level `build.gradle.kts`:

```Kotlin
dependencies {
    implementation(libs.compose.settings)
}

```

### Using Standard Dependency Notation

If you are not using version catalogs, add this directly to your app's `build.gradle.kts`:

```Kotlin
dependencies {
    implementation("com.github.JulesPvx:compose_unified_material_expressive_settings:1.0.0")
}

```

## API Reference

The library exposes a single main composable, `SettingsSection`, which provides a type-safe DSL to construct your lists.

* `action(...)`: A standard clickable row. Accepts a title, optional subtitle, optional leading icon, and an onClick lambda.

* `switch(...)`: A toggleable row. Accepts a title, checked state, onCheckedChange lambda, optional subtitle, and optional leading icon.

* `item { shape -> ... }`: A generic slot for entirely custom layouts. It injects the computed `Shape` parameter so your custom layout respects the dynamic grouping corners.

## Usage Example

```Kotlin
import fr.paeelluu.composesettings.SettingsSection
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SettingsScreen() {
    var notificationsEnabled by remember { mutableStateOf(true) }

    Column {
        SettingsSection(title = "Account") {
            action(
                title = "Profile",
                subtitle = "Manage your public profile",
                icon = { Icon(Icons.Default.Person, contentDescription = null) },
                onClick = { /* Navigate to profile */ }
            )
            
            switch(
                title = "Push Notifications",
                subtitle = "Receive alerts on this device",
                checked = notificationsEnabled,
                icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
                onCheckedChange = { notificationsEnabled = it }
            )
        }
    }
}

```

## Styling

This library relies entirely on `androidx.compose.material3`. It does not force any custom colors.

* Backgrounds use `MaterialTheme.colorScheme.surfaceContainer`.

* Text uses `MaterialTheme.typography` and `onSurface`/`onSurfaceVariant` colors.

To change the look, simply wrap your UI in your standard app theme.

## License

This project is open-source and available under the MIT License.
