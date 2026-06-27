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
 * /home/paeelluu/StudioProjects/compose_unified_material_expressive_settings/sample/src/main/java/fr/paeelluu/composeunifiedsettingsui/DemoComponents.kt
 *
 * This file is part of the Compose Unified Material Expressive Settings Library.
 * Licensed under the Proprietary License.
 * All rights reserved.
 */

package fr.paeelluu.composeunifiedsettingsui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.paeelluu.compose_settings_ui.SettingsSectionScope

// --- Shared Component Samples for Previews and Demos ---

fun SettingsSectionScope.UserProfileSample() {
    userProfile(
        name = "Jules Pouvreaux",
        email = "jules@example.com",
        onClick = {},
        trailingContent = {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier = Modifier.size(8.dp)
            ) {}
        }
    )
}

fun SettingsSectionScope.ActionSample() {
    action(
        title = "Security Settings",
        subtitle = "Manage your password and 2FA",
        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
        onClick = {}
    )
}

fun SettingsSectionScope.SwitchSample(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    switch(
        title = "Bluetooth Beacon",
        subtitle = "Allow PC to discover this device",
        checked = checked,
        onCheckedChange = onCheckedChange,
        icon = { Icon(Icons.Default.Bluetooth, contentDescription = null) },
        trailingContent = {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Configure",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    )
}

fun SettingsSectionScope.CheckboxSample(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    checkbox(
        title = "Keep screen on",
        subtitle = "Prevent device sleep during sync",
        checked = checked,
        onCheckedChange = onCheckedChange
    )
}

fun SettingsSectionScope.TextFieldSample(value: String, onValueChange: (String) -> Unit) {
    textField(
        title = "Display Name",
        value = value,
        onValueChange = onValueChange,
        placeholder = "Enter your name",
        isError = value.isEmpty(),
        supportingText = if (value.isEmpty()) "Name cannot be empty" else null
    )
}

fun SettingsSectionScope.LinkSample() {
    link(
        title = "Help & Support",
        subtitle = "Documentation and FAQ",
        icon = { Icon(Icons.AutoMirrored.Filled.Help, contentDescription = null) },
        onClick = {}
    )
}

fun SettingsSectionScope.SegmentedButtonSample(selected: String, onSelected: (String) -> Unit) {
    segmentedButton(
        options = listOf("Battery", "Balanced", "Performance"),
        selectedOption = selected,
        onOptionSelected = onSelected
    )
}

fun SettingsSectionScope.StepperSample(value: Int, onValueChange: (Int) -> Unit) {
    stepper(
        title = "Max Connections",
        value = value,
        onValueChange = onValueChange,
        valueRange = 1..10
    )
}

fun SettingsSectionScope.SliderSample(value: Float, onValueChange: (Float) -> Unit) {
    slider(
        title = "Volume",
        value = value,
        onValueChange = onValueChange,
        icon = { Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null) }
    )
}

fun SettingsSectionScope.SliderPreciseSample(value: Float, onValueChange: (Float) -> Unit) {
    slider(
        title = "Brightness",
        subtitle = "Precise adjustment",
        value = value,
        onValueChange = onValueChange,
        enablePreciseControls = true,
        showMinMax = true,
        icon = { Icon(Icons.Default.Settings, contentDescription = null) }
    )
}

fun SettingsSectionScope.RangeSliderSample(value: ClosedFloatingPointRange<Float>, onValueChange: (ClosedFloatingPointRange<Float>) -> Unit) {
    rangeSlider(
        title = "Active Range",
        value = value,
        onValueChange = onValueChange,
        valueLabel = { "${(it * 100).toInt()}%" }
    )
}

fun SettingsSectionScope.ColorPickerSample(color: Color, onColorSelected: (Color) -> Unit) {
    colorPicker(
        title = "Accent Color",
        subtitle = "Choose your favorite flavor",
        selectedColor = color,
        onColorSelected = onColorSelected,
        colors = listOf(
            Color(0xFF6E7FDC), Color(0xFFE57373), Color(0xFF81C784),
            Color(0xFFFFB74D), Color(0xFFBA68C8), Color(0xFF4DB6AC),
            Color(0xFF90A4AE), Color(0xFFFFF176)
        )
    )
}

fun SettingsSectionScope.ExpandableGroupSample() {
    expandableGroup(
        title = "Advanced Preferences",
        icon = { Icon(Icons.Default.Notifications, contentDescription = null) }
    ) {
        action(title = "Item 1", onClick = {})
        action(title = "Item 2", onClick = {})
    }
}

fun SettingsSectionScope.SelectorSample(selected: String, onSelected: (String) -> Unit) {
    selector(
        title = "Theme",
        options = listOf("Light", "Dark", "System"),
        selectedOption = selected,
        onOptionSelected = onSelected
    )
}

fun SettingsSectionScope.DialogSelectorSample(selected: String, onSelected: (String) -> Unit) {
    dialogSelector(
        title = "Language",
        options = listOf("English", "French", "Spanish", "German", "Japanese"),
        selectedOption = selected,
        onOptionSelected = onSelected
    )
}

fun SettingsSectionScope.RadioGroupSample(selected: String, onSelected: (String) -> Unit) {
    radioButtonGroup(
        options = listOf("Option 1", "Option 2", "Option 3"),
        selectedOption = selected,
        onOptionSelected = onSelected
    )
}

fun SettingsSectionScope.MultiSelectListSample(selected: Set<String>, onSelectionChange: (Set<String>) -> Unit) {
    multiSelectList(
        options = listOf("Email", "SMS", "Push", "Post"),
        selectedOptions = selected,
        onSelectionChange = onSelectionChange
    )
}

fun SettingsSectionScope.KeywordEditorSample(keywords: List<String>, onAdd: (String) -> Unit, onRemove: (String) -> Unit) {
    keywordEditor(
        title = "Tags",
        placeholder = "Add a tag...",
        keywords = keywords,
        onAdd = onAdd,
        onRemove = onRemove
    )
}

fun SettingsSectionScope.InfoSample() {
    info(text = "All data is encrypted locally on this device.")
}

fun SettingsSectionScope.LoadingSample() {
    loading(title = "Fetching updates...", subtitle = "This may take a moment")
}

fun SettingsSectionScope.SearchBarSample(query: String, onQueryChange: (String) -> Unit) {
    searchBar(
        query = query,
        onQueryChange = onQueryChange,
        placeholder = "Filter settings..."
    )
}

fun SettingsSectionScope.FullScreenSearchSample(
    query: String,
    onQueryChange: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    items: List<String>
) {
    fullScreenSearch(
        query = query,
        onQueryChange = { onQueryChange(it) },
        onSearch = { onExpandedChange(false) },
        expanded = expanded,
        onExpandedChange = { onExpandedChange(it) },
        placeholder = "Search items...",
        content = {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(items) { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onQueryChange(item)
                                onExpandedChange(false)
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
    )
}

fun SettingsSectionScope.FooterSample() {
    footer(text = "Version 1.0.8 (Alpha)\n© 2026 Jules Pouvreaux")
}

fun SettingsSectionScope.ItemSample() {
    item { shape ->
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = shape,
            color = MaterialTheme.colorScheme.tertiaryContainer
        ) {
            Text("Custom Raw Item", modifier = Modifier.padding(vertical = 16.dp, horizontal = 24.dp))
        }
    }
}
