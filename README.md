![Compose settings visualization](https://github.com/JulesPvx/compose_unified_material_expressive_settings/blob/7693c0c663202a8f6646c625444a0534690fb13b/docs/assets/cover.png)

# Compose Unified Material Expressive Settings

A Jetpack Compose library for building grouped settings screens with a modern Material 3 Expressive aesthetic. It features a declarative DSL, adaptive corner rounding, and native support for Shared Element Transitions.

## Installation

The library is hosted on **Maven Central**.

### 1. Add Repository
Ensure `mavenCentral()` is in your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
```

### 2. Add Dependency

**Using Version Catalogs (Recommended):**
```toml
[versions]
composeSettings = "1.2.0"

[libraries]
compose-settings = { module = "io.github.julespvx:compose-settings-ui", version.ref = "composeSettings" }
```

**Standard Notation:**
```kotlin
dependencies {
    implementation("io.github.julespvx:compose-settings-ui:1.2.0")
}
```

## Quick Start

```kotlin
LazyColumn(modifier = Modifier.fillMaxSize()) {
    settingsSection(title = "App Preferences") {
        switch(
            title = "Dark Mode",
            checked = isDarkMode,
            onCheckedChange = { isDarkMode = it },
            icon = { Icon(Icons.Default.Brightness4, null) }
        )
        
        selector(
            title = "Language",
            options = listOf("English", "French", "Spanish"),
            selectedOption = language,
            onOptionSelected = { language = it }
        )
        
        link(title = "Privacy Policy", onClick = { /* ... */ })
    }
}
```

## Documentation

For a comprehensive guide, component catalog, and advanced usage examples, please visit the **[Project Wiki](https://github.com/JulesPvx/compose_unified_material_expressive_settings/wiki)**.

- **[Component Catalog](https://github.com/JulesPvx/compose_unified_material_expressive_settings/wiki/Components)**: Code examples for all 20+ components.
- **[Theming](https://github.com/JulesPvx/compose_unified_material_expressive_settings/wiki/Theming)**: Material 3 token mapping and shape logic.
- **[Advanced Usage](https://github.com/JulesPvx/compose_unified_material_expressive_settings/wiki/Advanced-Usage)**: Shared transitions and custom items.

## Visuals

### Adaptive Corner Rounding
![Adaptive Corner Rounding](https://github.com/JulesPvx/compose_unified_material_expressive_settings/blob/85db1914f5038847a985248dad5b71ff7cd8f1e3/docs/assets/adaptive_shapes.png)

### Shared Element Transitions
![Shared Element Transitions](https://github.com/JulesPvx/compose_unified_material_expressive_settings/blob/85db1914f5038847a985248dad5b71ff7cd8f1e3/docs/assets/shared_transitions.png)
## License

Licensed under the Apache License, Version 2.0.
