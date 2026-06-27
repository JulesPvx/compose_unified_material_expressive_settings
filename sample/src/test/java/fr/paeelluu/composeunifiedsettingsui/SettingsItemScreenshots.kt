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
 * /home/paeelluu/StudioProjects/compose_unified_material_expressive_settings/sample/src/test/java/fr/paeelluu/composeunifiedsettingsui/SettingsItemScreenshots.kt
 *
 * This file is part of the Compose Unified Material Expressive Settings Library.
 * Licensed under the Proprietary License.
 * All rights reserved.
 */

package fr.paeelluu.composeunifiedsettingsui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.platform.ComposeView
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import fr.paeelluu.compose_settings_ui.SettingsSectionScope
import fr.paeelluu.compose_settings_ui.settingsSection
import org.junit.Rule
import org.junit.Test

class SettingsItemScreenshots {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material.Light.NoActionBar",
        renderingMode = SessionParams.RenderingMode.SHRINK
    )

    private fun snapshot(content: SettingsSectionScope.() -> Unit) {
        paparazzi.snapshot {
            MaterialTheme {
                Surface(color = Color.Transparent) {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        settingsSection(content = content)
                    }
                }
            }
        }
    }

    @Test
    fun actionItem() {
        snapshot {
            action(
                title = "Security Settings",
                subtitle = "Manage your password and 2FA",
                icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                onClick = {}
            )
        }
    }

    @Test
    fun switchItem() {
        snapshot {
            switch(
                title = "Bluetooth Beacon",
                subtitle = "Allow PC to discover this device",
                checked = true,
                onCheckedChange = {},
                icon = { Icon(Icons.Default.Bluetooth, contentDescription = null) }
            )
        }
    }

    @Test
    fun checkboxItem() {
        snapshot {
            checkbox(
                title = "Keep screen on",
                subtitle = "Prevent device sleep during sync",
                checked = true,
                onCheckedChange = {}
            )
        }
    }

    @Test
    fun textFieldItem() {
        snapshot {
            textField(
                title = "Display Name",
                value = "Jules Pouvreaux",
                onValueChange = {},
                placeholder = "Enter your name"
            )
        }
    }

    @Test
    fun linkItem() {
        snapshot {
            link(
                title = "Help & Support",
                subtitle = "Documentation and FAQ",
                onClick = {}
            )
        }
    }

    @Test
    fun segmentedButtonItem() {
        snapshot {
            segmentedButton(
                options = listOf("Battery", "Balanced", "Performance"),
                selectedOption = "Balanced",
                onOptionSelected = {}
            )
        }
    }

    @Test
    fun stepperItem() {
        snapshot {
            stepper(
                title = "Max Connections",
                value = 5,
                onValueChange = {},
                valueRange = 1..10
            )
        }
    }

    @Test
    fun sliderItem() {
        snapshot {
            slider(
                title = "Volume",
                value = 0.5f,
                onValueChange = {}
            )
        }
    }

    @Test
    fun rangeSliderItem() {
        snapshot {
            rangeSlider(
                title = "Active Range",
                value = 0.2f..0.8f,
                onValueChange = {}
            )
        }
    }

    @Test
    fun colorPickerItem() {
        snapshot {
            colorPicker(
                title = "Accent Color",
                selectedColor = Color(0xFF6E7FDC),
                onColorSelected = {},
                colors = listOf(Color.Red, Color.Green, Color.Blue)
            )
        }
    }

    @Test
    fun userProfileItem() {
        snapshot {
            userProfile(
                name = "Jules Pouvreaux",
                email = "jules@example.com",
                onClick = {}
            )
        }
    }

    @Test
    fun expandableGroupItem() {
        snapshot {
            expandableGroup(
                title = "Advanced Preferences",
                icon = { Icon(Icons.Default.Notifications, contentDescription = null) }
            ) {
                action(title = "Inner Item", onClick = {})
            }
        }
    }

    @Test
    fun selectorItem() {
        snapshot {
            selector(
                title = "Theme",
                options = listOf("Light", "Dark", "System"),
                selectedOption = "System",
                onOptionSelected = {}
            )
        }
    }

    @Test
    fun dialogSelectorItem() {
        snapshot {
            dialogSelector(
                title = "Language",
                options = listOf("English", "French", "Spanish", "German", "Japanese"),
                selectedOption = "English",
                onOptionSelected = {}
            )
        }
    }

    @Test
    fun radioButtonGroupItem() {
        snapshot {
            radioButtonGroup(
                options = listOf("Option 1", "Option 2", "Option 3"),
                selectedOption = "Option 1",
                onOptionSelected = {}
            )
        }
    }

    @Test
    fun multiSelectListItem() {
        snapshot {
            multiSelectList(
                options = listOf("Email", "SMS", "Push", "Post"),
                selectedOptions = setOf("Email", "SMS"),
                onSelectionChange = {}
            )
        }
    }

    @Test
    fun timePickerItem() {
        snapshot {
            timePicker(
                title = "Reminder Time",
                hour = 8,
                minute = 30,
                onTimeSelected = { _, _ -> }
            )
        }
    }

    @Test
    fun datePickerItem() {
        snapshot {
            datePicker(
                title = "Birthday",
                selectedDateMillis = System.currentTimeMillis(),
                onDateSelected = {}
            )
        }
    }

    @Test
    fun infoItem() {
        snapshot {
            info(text = "All data is encrypted locally on this device.")
        }
    }

    @Test
    fun loadingItem() {
        snapshot {
            loading(title = "Fetching updates...", subtitle = "This may take a moment")
        }
    }

    @Test
    fun footerItem() {
        snapshot {
            footer(text = "Version 1.0.8 (Alpha)\n© 2026 Jules Pouvreaux")
        }
    }

    @Test
    fun subHeaderItem() {
        snapshot {
            subHeader(text = "Privacy Settings")
        }
    }

    @Test
    fun searchBarItem() {
        snapshot {
            searchBar(
                query = "",
                onQueryChange = {},
                placeholder = "Filter settings..."
            )
        }
    }

    @Test
    fun keywordEditorItem() {
        snapshot {
            keywordEditor(
                title = "Tags",
                placeholder = "Add a tag...",
                keywords = listOf("Compose", "Material", "Settings"),
                onAdd = {},
                onRemove = {}
            )
        }
    }

    @Test
    fun fullScreenSearchItem() {
        snapshot {
            fullScreenSearch(
                query = "",
                onQueryChange = {},
                onSearch = {},
                expanded = false,
                onExpandedChange = {},
                content = { Text("Search Content") }
            )
        }
    }

    @Test
    fun itemsSample() {
        snapshot {
            items(3) { index ->
                action(title = "Item $index", onClick = {})
            }
        }
    }

    @Test
    fun customItem() {
        snapshot {
            item { shape ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = shape,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                ) {
                    Text("Custom Raw Item", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }

    @Test
    fun fullLibraryDemo() {
        val composeView = ComposeView(paparazzi.context).apply {
            setContent {
                var time by remember { mutableStateOf(0L) }
                LaunchedEffect(Unit) {
                    val start = System.currentTimeMillis()
                    while (true) {
                        time = System.currentTimeMillis() - start
                        delay(16)
                    }
                }
                FullLibraryDemo(timeMillis = time)
            }
        }

        paparazzi.gif(
            view = composeView,
            name = "full_demo",
            start = 0L,
            end = 55000L,
            fps = 30
        )
    }

    @Test
    fun adaptiveShapesVisual() {
        val composeView = ComposeView(paparazzi.context).apply {
            setContent {
                var expanded by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    delay(500)
                    expanded = true
                }

                MaterialTheme {
                    Surface(color = Color.White) {
                        LazyColumn(modifier = Modifier.padding(16.dp)) {
                            settingsSection(title = "Adaptive Shapes Animation") {
                                expandableGroup(
                                    title = "Expanding Container",
                                    icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
                                    initiallyExpanded = expanded
                                ) {
                                    action(title = "Grouped Item 1", onClick = {})
                                    action(title = "Grouped Item 2", onClick = {})
                                }
                            }
                        }
                    }
                }
            }
        }

        paparazzi.gif(
            view = composeView,
            name = "adaptive_shapes",
            start = 0L,
            end = 2000L,
            fps = 30
        )
    }

    @Test
    fun sharedTransitionVisual() {
        val composeView = ComposeView(paparazzi.context).apply {
            setContent {
                var showDetails by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    delay(500)
                    showDetails = true
                }

                SharedTransitionSamplePreview(initialShowDetails = showDetails)
            }
        }

        paparazzi.gif(
            view = composeView,
            name = "shared_transitions",
            start = 0L,
            end = 2000L,
            fps = 30
        )
    }
}
