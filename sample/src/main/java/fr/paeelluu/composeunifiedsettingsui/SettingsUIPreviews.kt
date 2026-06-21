package fr.paeelluu.composeunifiedsettingsui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.paeelluu.compose_settings_ui.SettingsSection

@Preview(showBackground = true, name = "1. Action Item")
@Composable
private fun ActionItemPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SettingsSection(
                title = "Account",
                modifier = Modifier.padding(16.dp)
            ) {
                action(
                    title = "Profile Settings",
                    subtitle = "Update your email and password",
                    icon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
                    onClick = {}
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "2. Switch Item")
@Composable
private fun SwitchItemPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SettingsSection(
                title = "Notifications",
                modifier = Modifier.padding(16.dp)
            ) {
                switch(
                    title = "Push Notifications",
                    subtitle = "Receive alerts on your device",
                    checked = true,
                    onCheckedChange = {},
                    icon = { Icon(Icons.Default.Notifications, contentDescription = null) }
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "3. Selector Item")
@Composable
private fun SelectorItemPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SettingsSection(
                title = "Appearance",
                modifier = Modifier.padding(16.dp)
            ) {
                selector(
                    title = "App Theme",
                    subtitle = "Current: System Default",
                    options = listOf("Light", "Dark", "System Default"),
                    selectedOption = "System Default",
                    onOptionSelected = {},
                    icon = { Icon(Icons.Default.ColorLens, contentDescription = null) }
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "3. Slider Item")
@Composable
private fun SliderItemPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SettingsSection(
                title = "Volume",
                modifier = Modifier.padding(16.dp)
            ) {
                slider(
                    title = "Media Volume",
                    subtitle = "Adjust the media playback volume",
                    icon = {
                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null)
                    },
                    value = 0.5f,
                    valueLabel = { value -> "${(value * 100).toInt()}%" },
                    showMinMax = true,
                    onValueChange = {},
                    valueRange = 0f..1f,
                    steps = 10
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "4. Keyword Editor")
@Composable
private fun KeywordEditorPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            SettingsSection(
                title = "Keyword Editor",
                modifier = Modifier.padding(16.dp)
            ) {
                keywordEditor(
                    title = "Add Keywords",
                    keywords = listOf("Compose", "Kotlin", "UI"),
                    placeholder = "Enter a keyword",
                    onAdd = {},
                    onRemove = {}
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "4. Full Section (Grouped Shapes)", heightDp = 3000)
@Composable
fun FullSettingsScreenPreview() {
    var bluetoothEnabled by remember { mutableStateOf(true) }
    var selectedPowerMode by remember { mutableIntStateOf(1) }
    var volume by remember { mutableFloatStateOf(0.5f) }
    var displayName by remember { mutableStateOf("Jules") }
    var keepScreenOn by remember { mutableStateOf(false) }
    var selectedTheme by remember { mutableStateOf("System Default") }
    var maxConnections by remember { mutableIntStateOf(3) }
    var selectedLanguage by remember { mutableStateOf("English") }
    var notificationsEnabled by remember { mutableStateOf(setOf("Email", "Push")) }
    var searchPath by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var selectedColor by remember { mutableStateOf(Color(0xFF6E7FDC)) }
    var connectionRange by remember { mutableStateOf(0.2f..0.8f) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                SettingsSection(title = "Search") {
                    searchBar(
                        query = searchPath,
                        onQueryChange = { searchPath = it },
                        placeholder = "Search for a setting..."
                    )
                }

                SettingsSection(title = "Account") {
                    userProfile(
                        name = "Jules Pouvreaux",
                        email = "jules@example.com",
                        onClick = {}
                    )
                    
                    textField(
                        title = "Display Name",
                        value = displayName,
                        onValueChange = { displayName = it },
                        placeholder = "Enter your name",
                        isError = displayName.isEmpty(),
                        supportingText = if (displayName.isEmpty()) "Name cannot be empty" else null
                    )
                }

                SettingsSection(title = "Connectivity") {
                    switch(
                        title = "Bluetooth Beacon",
                        subtitle = "Allow PC to discover this device",
                        checked = bluetoothEnabled,
                        onCheckedChange = { bluetoothEnabled = it },
                        icon = { Icon(Icons.Default.Bluetooth, contentDescription = null) }
                    )

                    dialogSelector(
                        title = "Language",
                        options = listOf("English", "French", "Spanish", "German", "Japanese", "Chinese"),
                        selectedOption = selectedLanguage,
                        onOptionSelected = { selectedLanguage = it }
                    )

                    subHeader(text = "Advanced Connectivity")
                    
                    rangeSlider(
                        title = "Signal Strength Range",
                        value = connectionRange,
                        onValueChange = { connectionRange = it },
                        valueLabel = { "${(it * 100).toInt()}%" }
                    )
                }

                SettingsSection(title = "Appearance") {
                    segmentedButton(
                        options = listOf("Light", "Dark", "System Default"),
                        selectedOption = selectedTheme,
                        onOptionSelected = { selectedTheme = it }
                    )
                    
                    colorPicker(
                        title = "Accent Color",
                        subtitle = "Choose your favorite color",
                        selectedColor = selectedColor,
                        onColorSelected = { selectedColor = it }
                    )

                    slider(
                        title = "Volume",
                        value = volume,
                        onValueChange = { volume = it },
                        icon = { Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null) }
                    )
                }

                SettingsSection(title = "Preferences") {
                    subHeader(text = "Notifications")
                    multiSelectList(
                        options = listOf("Email", "Push", "SMS", "Desktop"),
                        selectedOptions = notificationsEnabled,
                        onSelectionChange = { notificationsEnabled = it }
                    )

                    subHeader(text = "Schedule")
                    datePicker(
                        title = "Sync Date",
                        selectedDateMillis = selectedDate,
                        onDateSelected = { selectedDate = it }
                    )
                    
                    stepper(
                        title = "Max Connections",
                        value = maxConnections,
                        onValueChange = { maxConnections = it },
                        valueRange = 1..10
                    )
                }

                SettingsSection(title = "General") {
                    checkbox(
                        title = "Keep screen on",
                        subtitle = "Prevent device sleep",
                        checked = keepScreenOn,
                        onCheckedChange = { keepScreenOn = it }
                    )

                    expandableGroup(
                        title = "Help & Support",
                        icon = { Icon(Icons.Default.Notifications, contentDescription = null) }
                    ) {
                        link(
                            title = "Documentation",
                            onClick = {}
                        )
                        link(
                            title = "Community Forum",
                            onClick = {}
                        )
                    }

                    loading(title = "System Update", subtitle = "Checking for updates...")

                    info(
                        text = "Your device is secured by biometric authentication."
                    )

                    link(
                        title = "Legal Information",
                        onClick = {},
                        icon = { Icon(Icons.AutoMirrored.Filled.Help, contentDescription = null) }
                    )

                    action(
                        title = "Factory Reset",
                        subtitle = "This will erase all data",
                        icon = { Icon(Icons.Default.Storage, contentDescription = null) },
                        onClick = {}
                    )
                }
                
                SettingsSection(title = "About") {
                    footer(text = "Version 1.0.8 (Alpha)\n© 2026 Jules Pouvreaux Labs")
                }
            }
        }
    }
}
