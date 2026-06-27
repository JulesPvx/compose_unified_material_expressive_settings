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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.paeelluu.compose_settings_ui.settingsSection

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SharedTransitionSamplePreview(initialShowDetails: Boolean = false) {
    var showDetails by remember { mutableStateOf(initialShowDetails) }
    LaunchedEffect(initialShowDetails) {
        showDetails = initialShowDetails
    }

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
                                    avatarSharedTransitionKey = "profile_avatar",
                                    titleSharedTransitionKey = "profile_name",
                                    subtitleSharedTransitionKey = "profile_email",
                                    onClick = { showDetails = true }
                                )

                                action(
                                    title = "Advanced Settings",
                                    subtitle = "Click me too",
                                    sharedTransitionKey = "action_card",
                                    titleSharedTransitionKey = "action_title",
                                    subtitleSharedTransitionKey = "action_subtitle",
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
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Surface(
                                modifier = Modifier
                                    .sharedBounds(
                                        rememberSharedContentState(key = "profile_card"),
                                        animatedVisibilityScope = this@AnimatedContent
                                    )
                                    .fillMaxWidth()
                                    .height(200.dp),
                                shape = RoundedCornerShape(28.dp),
                                color = MaterialTheme.colorScheme.surfaceContainer
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .sharedBounds(
                                                rememberSharedContentState(key = "profile_avatar"),
                                                animatedVisibilityScope = this@AnimatedContent
                                            )
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = "J",
                                                style = MaterialTheme.typography.headlineLarge,
                                                modifier = Modifier.sharedBounds(
                                                    rememberSharedContentState(key = "profile_avatar_text"),
                                                    animatedVisibilityScope = this@AnimatedContent
                                                )
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "Jules Pouvreaux",
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.sharedBounds(
                                            rememberSharedContentState(key = "profile_name"),
                                            animatedVisibilityScope = this@AnimatedContent
                                        )
                                    )
                                    Text(
                                        text = "jules@example.com",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.sharedBounds(
                                            rememberSharedContentState(key = "profile_email"),
                                            animatedVisibilityScope = this@AnimatedContent
                                        )
                                    )
                                }
                            }

                            Surface(
                                modifier = Modifier
                                    .sharedBounds(
                                        rememberSharedContentState(key = "action_card"),
                                        animatedVisibilityScope = this@AnimatedContent
                                    )
                                    .fillMaxWidth()
                                    .height(100.dp),
                                shape = RoundedCornerShape(28.dp),
                                color = MaterialTheme.colorScheme.surfaceContainer
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Advanced Settings",
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.sharedBounds(
                                            rememberSharedContentState(key = "action_title"),
                                            animatedVisibilityScope = this@AnimatedContent
                                        )
                                    )
                                    Text(
                                        text = "Click me too",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.sharedBounds(
                                            rememberSharedContentState(key = "action_subtitle"),
                                            animatedVisibilityScope = this@AnimatedContent
                                        )
                                    )
                                }
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

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Preview(showBackground = true, heightDp = 800)
@Composable
fun FullSettingsScreenPreview() {
    var bluetoothEnabled by remember { mutableStateOf(true) }
    var selectedPerformance by remember { mutableStateOf("Balanced") }
    var volume by remember { mutableFloatStateOf(0.5f) }
    var brightness by remember { mutableFloatStateOf(0.8f) }
    var accentColor by remember { mutableStateOf(Color(0xFF6E7FDC)) }
    var searchQuery by remember { mutableStateOf("") }
    var fullSearchQuery by remember { mutableStateOf("") }
    var fullSearchExpanded by remember { mutableStateOf(false) }

    var showProfileDetail by remember { mutableStateOf(false) }

    val sections = remember {
        listOf("Account", "System", "Customization", "About")
    }

    val filteredSections = remember(searchQuery) {
        sections.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    val fruits = remember { listOf("Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape") }
    val filteredFruits = remember(fullSearchQuery) {
        fruits.filter { it.contains(fullSearchQuery, ignoreCase = true) }
    }

    MaterialTheme {
        SharedTransitionLayout {
            AnimatedContent(targetState = showProfileDetail, label = "main_transition") { isDetailVisible ->
                if (!isDetailVisible) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            settingsSection(
                                title = "Search",
                                sharedTransitionScope = this@SharedTransitionLayout,
                                animatedVisibilityScope = this@AnimatedContent
                            ) {
                                searchBar(
                                    query = searchQuery,
                                    onQueryChange = { searchQuery = it },
                                    placeholder = "Search settings..."
                                )

                                fullScreenSearch(
                                    query = fullSearchQuery,
                                    onQueryChange = { fullSearchQuery = it },
                                    onSearch = { fullSearchExpanded = false },
                                    expanded = fullSearchExpanded,
                                    onExpandedChange = { fullSearchExpanded = it },
                                    placeholder = "Full screen search...",
                                    content = {
                                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                            items(filteredFruits) { item ->
                                                Text(
                                                    text = item,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .clickable {
                                                            fullSearchQuery = item
                                                            fullSearchExpanded = false
                                                        }
                                                        .padding(16.dp)
                                                )
                                            }
                                        }
                                    }
                                )
                            }

                            if (filteredSections.contains("Account")) {
                                settingsSection(
                                    title = "Account",
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@AnimatedContent,
                                    titleSharedTransitionKey = "account_section_title"
                                ) {
                                    userProfile(
                                        name = "Jules Pouvreaux",
                                        email = "jules@example.com",
                                        avatar = {
                                            Surface(
                                                shape = CircleShape,
                                                color = MaterialTheme.colorScheme.primaryContainer,
                                                modifier = Modifier.size(40.dp)
                                            ) {
                                                Box(contentAlignment = Alignment.Center) {
                                                    with(sharedTransitionScope!!) {
                                                        Text(
                                                            text = "J",
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                                            modifier = Modifier.sharedBounds(
                                                                rememberSharedContentState(key = "profile_avatar_text"),
                                                                animatedVisibilityScope = animatedVisibilityScope!!
                                                            )
                                                        )
                                                    }
                                                }
                                            }
                                        },
                                        sharedTransitionKey = "profile_card",
                                        avatarSharedTransitionKey = "profile_avatar",
                                        titleSharedTransitionKey = "profile_name",
                                        subtitleSharedTransitionKey = "profile_email",
                                        onClick = { showProfileDetail = true }
                                    )
                                action(
                                    title = "Security Settings",
                                    subtitle = "Manage your password and 2FA",
                                    icon = {
                                        with(sharedTransitionScope!!) {
                                            Icon(
                                                Icons.Default.Settings,
                                                contentDescription = null,
                                                modifier = Modifier.sharedElement(
                                                    rememberSharedContentState("security_icon"),
                                                    animatedVisibilityScope = animatedVisibilityScope!!
                                                )
                                            )
                                        }
                                    },
                                    sharedTransitionKey = "security_card",
                                    titleSharedTransitionKey = "security_title",
                                    subtitleSharedTransitionKey = "security_subtitle",
                                    onClick = { showProfileDetail = true }
                                )
                                }
                            }

                            if (filteredSections.contains("System")) {
                                settingsSection(
                                    title = "System",
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@AnimatedContent
                                ) {
                                    SwitchSample(bluetoothEnabled) { bluetoothEnabled = it }
                                    SegmentedButtonSample(selectedPerformance) { selectedPerformance = it }
                                    SliderSample(volume) { volume = it }
                                    SliderPreciseSample(brightness) { brightness = it }
                                }
                            }

                            if (filteredSections.contains("Customization")) {
                                settingsSection(
                                    title = "Customization",
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@AnimatedContent
                                ) {
                                    ColorPickerSample(accentColor) { accentColor = it }
                                    ExpandableGroupSample()
                                }
                            }

                            if (searchQuery.isEmpty()) {
                                settingsSection(
                                    title = "Disabled Section",
                                    enabled = false,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@AnimatedContent
                                ) {
                                    switch(title = "Disabled Switch", checked = true, onCheckedChange = {})
                                    slider(title = "Disabled Slider", value = 0.5f, onValueChange = {})
                                    action(title = "Disabled Action", onClick = {})
                                }
                            }

                            if (filteredSections.contains("About")) {
                                settingsSection(
                                    title = "About",
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@AnimatedContent
                                ) {
                                    footer(text = "Version 1.0.8 (Alpha)\n© 2026 Jules Pouvreaux")
                                }
                            }
                        }
                    }
                } else {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Account Details",
                                        modifier = Modifier.sharedElement(
                                            rememberSharedContentState(key = "account_section_title"),
                                            animatedVisibilityScope = this@AnimatedContent
                                        )
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = { showProfileDetail = false }) {
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
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Surface(
                                modifier = Modifier
                                    .sharedBounds(
                                        rememberSharedContentState(key = "profile_card"),
                                        animatedVisibilityScope = this@AnimatedContent
                                    )
                                    .fillMaxWidth()
                                    .height(200.dp),
                                shape = RoundedCornerShape(28.dp),
                                color = MaterialTheme.colorScheme.surfaceContainer
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .sharedBounds(
                                                rememberSharedContentState(key = "profile_avatar"),
                                                animatedVisibilityScope = this@AnimatedContent
                                            )
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = "J",
                                                style = MaterialTheme.typography.headlineLarge,
                                                modifier = Modifier.sharedBounds(
                                                    rememberSharedContentState(key = "profile_avatar_text"),
                                                    animatedVisibilityScope = this@AnimatedContent
                                                )
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "Jules Pouvreaux",
                                        style = MaterialTheme.typography.headlineSmall,
                                        modifier = Modifier.sharedBounds(
                                            rememberSharedContentState(key = "profile_name"),
                                            animatedVisibilityScope = this@AnimatedContent
                                        )
                                    )
                                    Text(
                                        text = "jules@example.com",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.sharedBounds(
                                            rememberSharedContentState(key = "profile_email"),
                                            animatedVisibilityScope = this@AnimatedContent
                                        )
                                    )
                                }
                            }

                            Surface(
                                modifier = Modifier
                                    .sharedBounds(
                                        rememberSharedContentState(key = "security_card"),
                                        animatedVisibilityScope = this@AnimatedContent
                                    )
                                    .fillMaxWidth()
                                    .height(100.dp),
                                shape = RoundedCornerShape(28.dp),
                                color = MaterialTheme.colorScheme.surfaceContainer
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Security Settings Details",
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.sharedBounds(
                                            rememberSharedContentState(key = "security_title"),
                                            animatedVisibilityScope = this@AnimatedContent
                                        )
                                    )
                                    Text(
                                        text = "Manage your password and 2FA",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.sharedBounds(
                                            rememberSharedContentState(key = "security_subtitle"),
                                            animatedVisibilityScope = this@AnimatedContent
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Adaptive Corner Rounding Demo")
@Composable
fun AdaptiveCornerRoundingPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Adaptive Corner Rounding",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    settingsSection(title = "Expanding Group") {
                        expandableGroup(
                            title = "Tap to see fluid corners",
                            icon = { Icon(Icons.Default.Notifications, contentDescription = null) },
                            initiallyExpanded = false
                        ) {
                            action(title = "Item 1", onClick = {})
                            action(title = "Item 2", onClick = {})
                        }
                    }

                    settingsSection(title = "Inline Selector") {
                        selector(
                            title = "Theme Selection",
                            options = listOf("Light", "Dark", "System"),
                            selectedOption = "System",
                            onOptionSelected = {}
                        )
                    }

                    item {
                        Text(
                            text = "Notice how the container corners smoothly transition from large (outer) to small (inner) to create a cohesive grouped appearance.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
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
    var searchExpanded by remember { mutableStateOf(false) }
    var radioSelected by remember { mutableStateOf("Option 1") }
    var multiSelected by remember { mutableStateOf(setOf("Email")) }

    val allItems = listOf("Apple", "Banana", "Cherry", "Date")
    val filteredItems = allItems.filter { it.contains(query, ignoreCase = true) }

    MaterialTheme {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            settingsSection(title = "Advanced & Feedback") {
                FullScreenSearchSample(
                    query = query,
                    onQueryChange = { query = it },
                    expanded = searchExpanded,
                    onExpandedChange = { searchExpanded = it },
                    items = filteredItems
                )
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
