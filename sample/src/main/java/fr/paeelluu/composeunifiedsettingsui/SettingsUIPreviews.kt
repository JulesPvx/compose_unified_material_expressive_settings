package fr.paeelluu.composeunifiedsettingsui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.paeelluu.compose_settings_ui.SettingsSection
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
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            SettingsSection(
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

@Preview(showBackground = true, heightDp = 800)
@Composable
fun FullSettingsScreenPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                SettingsSection(title = "Account") {
                    UserProfileSample()
                    ActionSample()
                }

                SettingsSection(title = "System") {
                    SwitchSample()
                    SegmentedButtonSample()
                    SliderSample()
                }

                SettingsSection(title = "Customization") {
                    ColorPickerSample()
                    ExpandableGroupSample()
                }

                SettingsSection(title = "About") {
                    FooterSample()
                }
            }
        }
    }
}

// --- Individual Component Samples (Private Extension Functions) ---

@Composable
private fun SettingsSectionScope.UserProfileSample() {
    userProfile(
        name = "Jules Pouvreaux",
        email = "jules@example.com",
        onClick = {}
    )
}

@Composable
private fun SettingsSectionScope.ActionSample() {
    action(
        title = "Security Settings",
        subtitle = "Manage your password and 2FA",
        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
        onClick = {}
    )
}

@Composable
private fun SettingsSectionScope.SwitchSample() {
    var checked by remember { mutableStateOf(true) }
    switch(
        title = "Bluetooth Beacon",
        subtitle = "Allow PC to discover this device",
        checked = checked,
        onCheckedChange = { checked = it },
        icon = { Icon(Icons.Default.Bluetooth, contentDescription = null) }
    )
}

@Composable
private fun SettingsSectionScope.CheckboxSample() {
    var checked by remember { mutableStateOf(false) }
    checkbox(
        title = "Keep screen on",
        subtitle = "Prevent device sleep during sync",
        checked = checked,
        onCheckedChange = { checked = it }
    )
}

@Composable
private fun SettingsSectionScope.TextFieldSample() {
    var text by remember { mutableStateOf("Jules") }
    textField(
        title = "Display Name",
        value = text,
        onValueChange = { text = it },
        placeholder = "Enter your name",
        isError = text.isEmpty(),
        supportingText = if (text.isEmpty()) "Name cannot be empty" else null
    )
}

@Composable
private fun SettingsSectionScope.LinkSample() {
    link(
        title = "Help & Support",
        subtitle = "Documentation and FAQ",
        icon = { Icon(Icons.AutoMirrored.Filled.Help, contentDescription = null) },
        onClick = {}
    )
}

@Composable
private fun SettingsSectionScope.SegmentedButtonSample() {
    var selected by remember { mutableStateOf("Balanced") }
    segmentedButton(
        options = listOf("Battery", "Balanced", "Performance"),
        selectedOption = selected,
        onOptionSelected = { selected = it }
    )
}

@Composable
private fun SettingsSectionScope.StepperSample() {
    var count by remember { mutableIntStateOf(3) }
    stepper(
        title = "Max Connections",
        value = count,
        onValueChange = { count = it },
        valueRange = 1..10
    )
}

@Composable
private fun SettingsSectionScope.SliderSample() {
    var value by remember { mutableFloatStateOf(0.5f) }
    slider(
        title = "Volume",
        value = value,
        onValueChange = { value = it },
        icon = { Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null) }
    )
}

@Composable
private fun SettingsSectionScope.RangeSliderSample() {
    var range by remember { mutableStateOf(0.2f..0.8f) }
    rangeSlider(
        title = "Active Range",
        value = range,
        onValueChange = { range = it },
        valueLabel = { "${(it * 100).toInt()}%" }
    )
}

@Composable
private fun SettingsSectionScope.ColorPickerSample() {
    var color by remember { mutableStateOf(Color(0xFF6E7FDC)) }
    colorPicker(
        title = "Accent Color",
        subtitle = "Choose your favorite flavor",
        selectedColor = color,
        onColorSelected = { color = it },
        colors = listOf(
            Color(0xFF6E7FDC), Color(0xFFE57373), Color(0xFF81C784),
            Color(0xFFFFB74D), Color(0xFFBA68C8), Color(0xFF4DB6AC),
            Color(0xFF90A4AE), Color(0xFFFFF176)
        )
    )
}

@Composable
private fun SettingsSectionScope.ExpandableGroupSample() {
    expandableGroup(
        title = "Advanced Preferences",
        icon = { Icon(Icons.Default.Notifications, contentDescription = null) }
    ) {
        SwitchSample()
        CheckboxSample()
    }
}

@Composable
private fun SettingsSectionScope.SelectorSample() {
    var selected by remember { mutableStateOf("System") }
    selector(
        title = "Theme",
        options = listOf("Light", "Dark", "System"),
        selectedOption = selected,
        onOptionSelected = { selected = it }
    )
}

@Composable
private fun SettingsSectionScope.DialogSelectorSample() {
    var selected by remember { mutableStateOf("English") }
    dialogSelector(
        title = "Language",
        options = listOf("English", "French", "Spanish", "German", "Japanese"),
        selectedOption = selected,
        onOptionSelected = { selected = it }
    )
}

@Composable
private fun SettingsSectionScope.RadioGroupSample() {
    var selected by remember { mutableStateOf("Option 1") }
    radioButtonGroup(
        options = listOf("Option 1", "Option 2", "Option 3"),
        selectedOption = selected,
        onOptionSelected = { selected = it }
    )
}

@Composable
private fun SettingsSectionScope.MultiSelectListSample() {
    var selected by remember { mutableStateOf(setOf("Email")) }
    multiSelectList(
        options = listOf("Email", "SMS", "Push", "Post"),
        selectedOptions = selected,
        onSelectionChange = { selected = it }
    )
}

@Composable
private fun SettingsSectionScope.KeywordEditorSample() {
    var keywords by remember { mutableStateOf(listOf("Compose", "Material")) }
    keywordEditor(
        title = "Tags",
        placeholder = "Add a tag...",
        keywords = keywords,
        onAdd = { keywords = keywords + it },
        onRemove = { keywords = keywords - it }
    )
}

@Composable
private fun SettingsSectionScope.InfoSample() {
    info(text = "All data is encrypted locally on this device.")
}

@Composable
private fun SettingsSectionScope.LoadingSample() {
    loading(title = "Fetching updates...", subtitle = "This may take a moment")
}

@Composable
private fun SettingsSectionScope.SearchBarSample() {
    var query by remember { mutableStateOf("") }
    searchBar(
        query = query,
        onQueryChange = { query = it },
        placeholder = "Filter settings..."
    )
}

@Composable
private fun SettingsSectionScope.FooterSample() {
    footer(text = "Version 1.0.8 (Alpha)\n© 2026 Jules Pouvreaux")
}

@Composable
private fun SettingsSectionScope.ItemSample() {
    item { shape ->
        Surface(
            modifier = Modifier.size(100.dp, 40.dp),
            shape = shape,
            color = MaterialTheme.colorScheme.tertiaryContainer
        ) {
            Text("Custom Raw Item", modifier = Modifier.padding(8.dp))
        }
    }
}

// --- Individual Component Previews ---

@Preview(showBackground = true, name = "Components - Basic")
@Composable
private fun BasicComponentsPreview() {
    MaterialTheme {
        SettingsSection(title = "Basic Controls", modifier = Modifier.padding(16.dp)) {
            ActionSample()
            SwitchSample()
            CheckboxSample()
            LinkSample()
        }
    }
}

@Preview(showBackground = true, name = "Components - Input")
@Composable
private fun InputComponentsPreview() {
    MaterialTheme {
        SettingsSection(title = "Inputs", modifier = Modifier.padding(16.dp)) {
            TextFieldSample()
            StepperSample()
            KeywordEditorSample()
        }
    }
}

@Preview(showBackground = true, name = "Components - Selection")
@Composable
private fun SelectionComponentsPreview() {
    MaterialTheme {
        SettingsSection(title = "Selection", modifier = Modifier.padding(16.dp)) {
            SegmentedButtonSample()
            SelectorSample()
            DialogSelectorSample()
        }
    }
}

@Preview(showBackground = true, name = "Components - Specialized")
@Composable
private fun SpecializedComponentsPreview() {
    MaterialTheme {
        SettingsSection(title = "Specialized", modifier = Modifier.padding(16.dp)) {
            ColorPickerSample()
            SliderSample()
            RangeSliderSample()
        }
    }
}

@Preview(showBackground = true, name = "Components - Advanced & Feedback")
@Composable
private fun AdvancedComponentsPreview() {
    MaterialTheme {
        SettingsSection(title = "Advanced & Feedback", modifier = Modifier.padding(16.dp)) {
            SearchBarSample()
            RadioGroupSample()
            MultiSelectListSample()
            InfoSample()
            LoadingSample()
            ItemSample()
        }
    }
}
