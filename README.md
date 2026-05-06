# Compose Unified Material Expressive Settings UI

A minimalist Jetpack Compose library for building grouped settings screens. It dynamically calculates corner radius to create visually distinct groups of settings items, automatically inheriting your app's Material 3 theme.

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

* `selector(...)`: An inline, smoothly expanding selector row. Accepts a title, a generic list of `options` (`<T>`), the currently `selectedOption`, an `onOptionSelected` lambda, an optional `displayText` lambda to format the generic items for the UI, an optional subtitle, and an optional leading icon.

* `item { shape -> ... }`: A generic slot for entirely custom layouts. It injects the computed `Shape` parameter so your custom layout respects the dynamic grouping corners.

## Usage Example

```kotlin
import fr.paeelluu.compose_settings_ui.SettingsSection
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var themeOption by remember { mutableIntStateOf(0) }

    val formatTheme: (Int) -> String = { mode ->
        when (mode) {
            0 -> "System Default"
            1 -> "Light Mode"
            2 -> "Dark Mode"
            else -> "Unknown"
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        SettingsSection(title = "Account & Preferences") {
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

            selector(
                title = "App Theme",
                subtitle = "Choose your preferred color mode",
                options = listOf(0, 1, 2),
                selectedOption = themeOption,
                onOptionSelected = { themeOption = it },
                displayText = formatTheme,
                icon = { Icon(Icons.Default.ColorLens, contentDescription = null) }
            )
        }

        SettingsSection(title = "Danger Zone") {
            item { shape ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape)
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .clickable { /* Handle delete account */ }
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Delete Account",
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
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
