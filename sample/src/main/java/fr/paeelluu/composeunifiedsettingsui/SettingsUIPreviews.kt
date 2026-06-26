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
 * /home/paeelluu/StudioProjects/compose_unified_material_expressive_settings/sample/src/main/java/fr/paeelluu/composeunifiedsettingsui/SettingsUIPreviews.kt
 *
 * This file is part of the Compose Unified Material Expressive Settings Library.
 * Licensed under the Proprietary License.
 * All rights reserved.
 */

package fr.paeelluu.composeunifiedsettingsui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.paeelluu.compose_settings_ui.settingsSection
import fr.paeelluu.compose_settings_ui.SettingsSectionScope

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SharedTransitionSamplePreview() {
    var showDetails by remember { mutableStateOf(false) }

    MaterialTheme {
        SharedTransitionLayout {
            AnimatedContent(
                targetState = showDetails,
                label = "transition",
            ) { isDetailsVisible ->
                if (!isDetailsVisible) {
                    Scaffold(
                        topBar = { TopAppBar(title = { Text("Settings") }) }
                    ) { innerPadding ->
                        LazyColumn(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            settingsSection(
                                title = "Shared Transitions",
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedVisibilityScope = this@AnimatedContent
                            ) {
                                userProfile(
                                    name = "Jules Pouvreaux",
                                    email = "Tap to see transition",
                                    sharedTransitionKey = "profile_card",
                                    onClick = { showDetails = true }
                                )

                                action(
                                    title = "Advanced Settings",
                                    subtitle = "Click me too",
                                    sharedTransitionKey = "action_card",
                                    onClick = { showDetails = true }
                                )
                            }
                        }
                    }
                } else {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Details") },
                                navigationIcon = {
                                    IconButton(onClick = { showDetails = false }) {
                                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .sharedBounds(
                                        rememberSharedContentState(key = "profile_card"),
                                        animatedVisibilityScope = this@AnimatedContent
                                    )
                                    .size(200.dp)
                                    .clip(RoundedCornerShape(28.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Profile Details", style = MaterialTheme.typography.headlineMedium)
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            Box(
                                modifier = Modifier
                                    .sharedBounds(
                                        rememberSharedContentState(key = "action_card"),
                                        animatedVisibilityScope = this@AnimatedContent
                                    )
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.secondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Expanded Action Info")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FullScreenSearchSamplePreview() {
    var searchQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val allItems = remember {
        listOf("Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape")
    }
    val filteredItems = remember(searchQuery) {
        allItems.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    MaterialTheme {
        Scaffold { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
                settingsSection(title = "Full Screen Search") {
                    fullScreenSearch(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onSearch = { expanded = false },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        placeholder = "Search fruits...",
                        content = {
                            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                items(filteredItems) { item ->
                                    Text(
                                        text = item,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { 
                                                searchQuery = item
                                                expanded = false 
                                            }
                                            .padding(16.dp)
                                    )
                                }
                            }
                        }
                    )
                    
                    if (searchQuery.isNotEmpty()) {
                        item {
                            Text("Selected fruit: $searchQuery", modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchableSettingsPreview() {
    var searchQuery by remember { mutableStateOf("") }
    val allItems = remember {
        listOf(
            "Bluetooth" to "Connect to wireless devices",
            "Wi-Fi" to "Manage internet connections",
            "Display" to "Brightness, sleep, font size",
            "Battery" to "Power saving, usage statistics",
            "Storage" to "Manage apps and files",
            "Security" to "Screen lock, fingerprint, face unlock",
        )
    }

    val filteredItems = remember(searchQuery) {
        allItems.filter {
            it.first.contains(searchQuery, ignoreCase = true) ||
                    it.second.contains(searchQuery, ignoreCase = true)
        }
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                settingsSection(title = "Searchable Settings") {
                    searchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        placeholder = "Search settings..."
                    )

                    filteredItems.forEach { (title, subtitle) ->
                        action(title = title, subtitle = subtitle, onClick = {})
                    }

                    if (filteredItems.isEmpty()) {
                        item { shape ->
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = shape,
                                color = MaterialTheme.colorScheme.surfaceContainer
                            ) {
                                Text(
                                    text = "No results found for \"$searchQuery\"",
                                    modifier = Modifier.padding(24.dp),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 800)
@Composable
fun FullSettingsScreenPreview() {
    var bluetoothEnabled by remember { mutableStateOf(true) }
    var selectedPerformance by remember { mutableStateOf("Balanced") }
    var volume by remember { mutableFloatStateOf(0.5f) }
    var brightness by remember { mutableFloatStateOf(0.8f) }
    var accentColor by remember { mutableStateOf(Color(0xFF6E7FDC)) }
    var searchQuery by remember { mutableStateOf("") }

    val sections = remember {
        listOf("Account", "System", "Customization", "About")
    }

    val filteredSections = remember(searchQuery) {
        sections.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                settingsSection(title = "Search") {
                    searchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        placeholder = "Search settings..."
                    )
                }

                if (filteredSections.contains("Account")) {
                    settingsSection(title = "Account") {
                        UserProfileSample()
                        ActionSample()
                    }
                }

                if (filteredSections.contains("System")) {
                    settingsSection(title = "System") {
                        SwitchSample(bluetoothEnabled) { bluetoothEnabled = it }
                        SegmentedButtonSample(selectedPerformance) { selectedPerformance = it }
                        SliderSample(volume) { volume = it }
                        SliderPreciseSample(brightness) { brightness = it }
                    }
                }

                if (filteredSections.contains("Customization")) {
                    settingsSection(title = "Customization") {
                        ColorPickerSample(accentColor) { accentColor = it }
                        ExpandableGroupSample()
                    }
                }

                if (searchQuery.isEmpty()) {
                    settingsSection(
                        title = "Disabled Section",
                        enabled = false
                    ) {
                        switch(title = "Disabled Switch", checked = true, onCheckedChange = {})
                        slider(title = "Disabled Slider", value = 0.5f, onValueChange = {})
                        action(title = "Disabled Action", onClick = {})
                    }
                }

                if (filteredSections.contains("About")) {
                    settingsSection(title = "About") {
                        FooterSample()
                    }
                }
            }
        }
    }
}

// --- Individual Component Samples (Private Scoped Extensions) ---

private fun SettingsSectionScope.UserProfileSample() {
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

private fun SettingsSectionScope.ActionSample() {
    action(
        title = "Security Settings",
        subtitle = "Manage your password and 2FA",
        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
        onClick = {}
    )
}

private fun SettingsSectionScope.SwitchSample(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
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

private fun SettingsSectionScope.CheckboxSample(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    checkbox(
        title = "Keep screen on",
        subtitle = "Prevent device sleep during sync",
        checked = checked,
        onCheckedChange = onCheckedChange
    )
}

private fun SettingsSectionScope.TextFieldSample(value: String, onValueChange: (String) -> Unit) {
    textField(
        title = "Display Name",
        value = value,
        onValueChange = onValueChange,
        placeholder = "Enter your name",
        isError = value.isEmpty(),
        supportingText = if (value.isEmpty()) "Name cannot be empty" else null
    )
}

private fun SettingsSectionScope.LinkSample() {
    link(
        title = "Help & Support",
        subtitle = "Documentation and FAQ",
        icon = { Icon(Icons.AutoMirrored.Filled.Help, contentDescription = null) },
        onClick = {}
    )
}

private fun SettingsSectionScope.SegmentedButtonSample(selected: String, onSelected: (String) -> Unit) {
    segmentedButton(
        options = listOf("Battery", "Balanced", "Performance"),
        selectedOption = selected,
        onOptionSelected = onSelected
    )
}

private fun SettingsSectionScope.StepperSample(value: Int, onValueChange: (Int) -> Unit) {
    stepper(
        title = "Max Connections",
        value = value,
        onValueChange = onValueChange,
        valueRange = 1..10
    )
}

private fun SettingsSectionScope.SliderSample(value: Float, onValueChange: (Float) -> Unit) {
    slider(
        title = "Volume",
        value = value,
        onValueChange = onValueChange,
        icon = { Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null) }
    )
}

private fun SettingsSectionScope.SliderPreciseSample(value: Float, onValueChange: (Float) -> Unit) {
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

private fun SettingsSectionScope.RangeSliderSample(value: ClosedFloatingPointRange<Float>, onValueChange: (ClosedFloatingPointRange<Float>) -> Unit) {
    rangeSlider(
        title = "Active Range",
        value = value,
        onValueChange = onValueChange,
        valueLabel = { "${(it * 100).toInt()}%" }
    )
}

private fun SettingsSectionScope.ColorPickerSample(color: Color, onColorSelected: (Color) -> Unit) {
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

private fun SettingsSectionScope.ExpandableGroupSample() {
    expandableGroup(
        title = "Advanced Preferences",
        icon = { Icon(Icons.Default.Notifications, contentDescription = null) }
    ) {
        action(title = "Item 1", onClick = {})
        action(title = "Item 2", onClick = {})
    }
}

private fun SettingsSectionScope.SelectorSample(selected: String, onSelected: (String) -> Unit) {
    selector(
        title = "Theme",
        options = listOf("Light", "Dark", "System"),
        selectedOption = selected,
        onOptionSelected = onSelected
    )
}

private fun SettingsSectionScope.DialogSelectorSample(selected: String, onSelected: (String) -> Unit) {
    dialogSelector(
        title = "Language",
        options = listOf("English", "French", "Spanish", "German", "Japanese"),
        selectedOption = selected,
        onOptionSelected = onSelected
    )
}

private fun SettingsSectionScope.RadioGroupSample(selected: String, onSelected: (String) -> Unit) {
    radioButtonGroup(
        options = listOf("Option 1", "Option 2", "Option 3"),
        selectedOption = selected,
        onOptionSelected = onSelected
    )
}

private fun SettingsSectionScope.MultiSelectListSample(selected: Set<String>, onSelectionChange: (Set<String>) -> Unit) {
    multiSelectList(
        options = listOf("Email", "SMS", "Push", "Post"),
        selectedOptions = selected,
        onSelectionChange = onSelectionChange
    )
}

private fun SettingsSectionScope.KeywordEditorSample(keywords: List<String>, onAdd: (String) -> Unit, onRemove: (String) -> Unit) {
    keywordEditor(
        title = "Tags",
        placeholder = "Add a tag...",
        keywords = keywords,
        onAdd = onAdd,
        onRemove = onRemove
    )
}

private fun SettingsSectionScope.InfoSample() {
    info(text = "All data is encrypted locally on this device.")
}

private fun SettingsSectionScope.LoadingSample() {
    loading(title = "Fetching updates...", subtitle = "This may take a moment")
}

private fun SettingsSectionScope.SearchBarSample(query: String, onQueryChange: (String) -> Unit) {
    searchBar(
        query = query,
        onQueryChange = onQueryChange,
        placeholder = "Filter settings..."
    )
}

private fun SettingsSectionScope.FooterSample() {
    footer(text = "Version 1.0.8 (Alpha)\n© 2026 Jules Pouvreaux")
}

private fun SettingsSectionScope.ItemSample() {
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

// --- Individual Component Previews ---

@Preview(showBackground = true, name = "Components - Basic")
@Composable
private fun BasicComponentsPreview() {
    var switchState by remember { mutableStateOf(true) }
    var checkboxState by remember { mutableStateOf(false) }

    MaterialTheme {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            settingsSection(title = "Basic Controls") {
                ActionSample()
                SwitchSample(switchState) { switchState = it }
                CheckboxSample(checkboxState) { checkboxState = it }
                LinkSample()
            }
        }
    }
}

@Preview(showBackground = true, name = "Components - Input")
@Composable
private fun InputComponentsPreview() {
    var text by remember { mutableStateOf("Jules") }
    var count by remember { mutableIntStateOf(3) }
    var keywords by remember { mutableStateOf(listOf("Compose")) }

    MaterialTheme {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            settingsSection(title = "Inputs") {
                TextFieldSample(text) { text = it }
                StepperSample(count) { count = it }
                KeywordEditorSample(keywords, { keywords = keywords + it }, { keywords = keywords - it })
            }
        }
    }
}

@Preview(showBackground = true, name = "Components - Selection")
@Composable
private fun SelectionComponentsPreview() {
    var segSelected by remember { mutableStateOf("Balanced") }
    var selSelected by remember { mutableStateOf("System") }
    var dialSelected by remember { mutableStateOf("English") }

    MaterialTheme {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            settingsSection(title = "Selection") {
                SegmentedButtonSample(segSelected) { segSelected = it }
                SelectorSample(selSelected) { selSelected = it }
                DialogSelectorSample(dialSelected) { dialSelected = it }
            }
        }
    }
}

@Preview(showBackground = true, name = "Components - Specialized")
@Composable
private fun SpecializedComponentsPreview() {
    var color by remember { mutableStateOf(Color(0xFF6E7FDC)) }
    var vol by remember { mutableFloatStateOf(0.5f) }
    var range by remember { mutableStateOf(0.2f..0.8f) }

    MaterialTheme {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            settingsSection(title = "Specialized") {
                ColorPickerSample(color) { color = it }
                SliderSample(vol) { vol = it }
                RangeSliderSample(range) { range = it }
            }
        }
    }
}

@Preview(showBackground = true, name = "Components - Advanced & Feedback")
@Composable
private fun AdvancedComponentsPreview() {
    var query by remember { mutableStateOf("") }
    var radioSelected by remember { mutableStateOf("Option 1") }
    var multiSelected by remember { mutableStateOf(setOf("Email")) }

    MaterialTheme {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            settingsSection(title = "Advanced & Feedback") {
                SearchBarSample(query) { query = it }
                RadioGroupSample(radioSelected) { radioSelected = it }
                MultiSelectListSample(multiSelected) { multiSelected = it }
                InfoSample()
                LoadingSample()
                ItemSample()
            }
        }
    }
}
