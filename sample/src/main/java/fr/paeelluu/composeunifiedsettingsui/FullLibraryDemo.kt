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
 * /home/paeelluu/StudioProjects/compose_unified_material_expressive_settings/sample/src/main/java/fr/paeelluu/composeunifiedsettingsui/FullLibraryDemo.kt
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import fr.paeelluu.compose_settings_ui.settingsSection

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FullLibraryDemo(timeMillis: Long) {
    var bluetoothEnabled by remember { mutableStateOf(false) }
    var volume by remember { mutableFloatStateOf(0.3f) }
    var expanded by remember { mutableStateOf(false) }
    var showDetail by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    var notificationCount by remember { mutableIntStateOf(3) }
    var selectedTheme by remember { mutableStateOf("System") }
    var selectorExpanded by remember { mutableStateOf(false) }
    var lockScreenNotifications by remember { mutableStateOf(true) }
    var accentColor by remember { mutableStateOf(Color(0xFF6E7FDC)) }
    var segmentedOption by remember { mutableStateOf("Standard") }
    var selectedLanguage by remember { mutableStateOf("English") }
    var colorPickerOpened by remember { mutableStateOf(false) }
    var languageDialogOpened by remember { mutableStateOf(false) }

    val lazyListState = rememberLazyListState()
    var scrollTarget by remember { mutableIntStateOf(0) }

    LaunchedEffect(timeMillis) {
        // --- 0s - 5s: Profile Shared Transition ---
        if (timeMillis < 5000) {
            scrollTarget = 1
            showDetail = timeMillis in 1500..4000
        }

        // --- 6s - 10s: Search interaction ---
        if (timeMillis in 6000..10000) {
            scrollTarget = 2
            if (timeMillis > 7000) {
                val fullQuery = "Display"
                val charIndex = ((timeMillis - 7000) / 300).toInt().coerceAtMost(fullQuery.length)
                query = fullQuery.take(charIndex)
            }
        } else if (timeMillis < 6000) {
            query = ""
        }

        // --- 11s - 15s: Connectivity Toggles ---
        if (timeMillis in 11000..15000) {
            scrollTarget = 4
            bluetoothEnabled = timeMillis in 12500..14500
        } else if (timeMillis < 11000) {
            bluetoothEnabled = false
        }

        // --- 16s - 20s: Media Slider ---
        if (timeMillis in 16000..20000) {
            scrollTarget = 7
            if (timeMillis > 17500) {
                val progress = (timeMillis - 17500).toFloat() / 2000f
                volume = (progress * 4).toInt() / 4f
            }
        } else if (timeMillis < 16000) {
            volume = 0.3f
        }

        // --- 21s - 25s: Theme Selection (Selector) ---
        if (timeMillis in 21000..25000) {
            scrollTarget = 8
            selectorExpanded = timeMillis in 22000..24500
            if (timeMillis > 23500) selectedTheme = "Dark"
            else if (timeMillis > 22500) selectedTheme = "Light"
        } else if (timeMillis < 21000) {
            selectedTheme = "System"
            selectorExpanded = false
        }

        // --- 26s - 34s: Expandable Group + Stepper + Checkbox ---
        if (timeMillis in 26000..34000) {
            scrollTarget = 9
            expanded = timeMillis > 27500
            
            if (timeMillis in 29000..31000) {
                notificationCount = 3 + ((timeMillis - 29000) / 700).toInt().coerceAtMost(2)
            }
            if (timeMillis > 32000) {
                lockScreenNotifications = false
            }
        } else if (timeMillis < 26000) {
            expanded = false
            notificationCount = 3
            lockScreenNotifications = true
        }

        // --- 35s - 39s: Color Picker ---
        if (timeMillis in 35000..39500) {
            scrollTarget = 13 // Offset by expanded items
            colorPickerOpened = timeMillis in 36000..38500
            if (timeMillis > 37500) accentColor = Color.Green
        } else if (timeMillis < 35000) {
            accentColor = Color(0xFF6E7FDC)
            colorPickerOpened = false
        }

        // --- 40s - 44s: Segmented Button ---
        if (timeMillis in 40000..44500) {
            scrollTarget = 14
            if (timeMillis > 42000) segmentedOption = "Vibrant"
        } else if (timeMillis < 40000) {
            segmentedOption = "Standard"
        }

        // --- 45s - 49s: Dialog Selector ---
        if (timeMillis in 45000..50500) {
            scrollTarget = 18
            languageDialogOpened = timeMillis in 46000..49500
            if (timeMillis > 48000) selectedLanguage = "French"
        } else if (timeMillis < 45000) {
            selectedLanguage = "English"
            languageDialogOpened = false
        }

        // --- 51s - 55s: Final Scroll to bottom ---
        if (timeMillis > 51000) {
            scrollTarget = 20
        }
    }

    LaunchedEffect(scrollTarget) {
        lazyListState.animateScrollToItem(scrollTarget)
    }

    MaterialTheme {
        SharedTransitionLayout {
            AnimatedContent(targetState = showDetail, label = "demo_transition") { isDetail ->
                if (!isDetail) {
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            TopAppBar(title = { Text("Settings Demo") })

                            LazyColumn(
                                state = lazyListState,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                settingsSection(
                                    title = "Account & Search",
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this@AnimatedContent
                                ) {
                                    userProfile(
                                        name = "John Doe",
                                        email = "john@example.com",
                                        avatar = {
                                            Surface(
                                                shape = CircleShape,
                                                color = MaterialTheme.colorScheme.primaryContainer,
                                                modifier = Modifier.size(40.dp)
                                            ) {
                                                Box(contentAlignment = Alignment.Center) {
                                                    Text(
                                                        text = "J",
                                                        modifier = Modifier.sharedBounds(
                                                            rememberSharedContentState("demo_avatar_text"),
                                                            this@AnimatedContent
                                                        )
                                                    )
                                                }
                                            }
                                        },
                                        sharedTransitionKey = "demo_profile",
                                        onClick = { showDetail = true }
                                    )
                                    searchBar(query = query, onQueryChange = { query = it }, placeholder = "Search...")
                                }

                                settingsSection(title = "Connectivity") {
                                    switch(
                                        title = "Bluetooth",
                                        subtitle = if (bluetoothEnabled) "Discoverable as Pixel 8" else "Off",
                                        checked = bluetoothEnabled,
                                        onCheckedChange = { bluetoothEnabled = it },
                                        icon = { Icon(Icons.Default.Bluetooth, null) }
                                    )
                                    action(
                                        title = "Wi-Fi", 
                                        subtitle = if (bluetoothEnabled) "Connected to 'Home_Network'" else "Scan for networks", 
                                        enabled = bluetoothEnabled,
                                        onClick = {}
                                    )
                                }

                                settingsSection(title = "Device") {
                                    slider(
                                        title = "Media Volume",
                                        value = volume,
                                        onValueChange = { volume = it },
                                        steps = 4,
                                        icon = { Icon(Icons.AutoMirrored.Filled.VolumeUp, null) }
                                    )
                                    selector(
                                        title = "Display Theme",
                                        options = listOf("System", "Light", "Dark"),
                                        selectedOption = selectedTheme,
                                        onOptionSelected = { selectedTheme = it },
                                        initiallyExpanded = selectorExpanded
                                    )
                                    expandableGroup(
                                        title = "Notifications",
                                        initiallyExpanded = expanded,
                                        icon = { Icon(Icons.Default.Notifications, null) }
                                    ) {
                                        stepper(
                                            title = "Priority level", 
                                            value = notificationCount, 
                                            valueRange = 1..5, 
                                            onValueChange = { notificationCount = it }
                                        )
                                        checkbox(
                                            title = "Show on lock screen", 
                                            checked = lockScreenNotifications, 
                                            onCheckedChange = { lockScreenNotifications = it }
                                        )
                                    }
                                }

                                settingsSection(title = "Appearance") {
                                    colorPicker(
                                        title = "Accent Color",
                                        selectedColor = accentColor,
                                        onColorSelected = { accentColor = it },
                                        colors = listOf(Color.Red, Color.Blue, Color(0xFF6E7FDC), Color.Green),
                                        initiallyOpened = colorPickerOpened
                                    )
                                    segmentedButton(
                                        options = listOf("Standard", "Vibrant", "Natural"),
                                        selectedOption = segmentedOption,
                                        onOptionSelected = { segmentedOption = it }
                                    )
                                }

                                settingsSection(title = "System") {
                                    info(text = "Software is up to date (Android 15)")
                                    link(title = "About Phone", onClick = {})
                                    dialogSelector(
                                        title = "Language",
                                        options = listOf("English", "French", "Spanish"),
                                        selectedOption = selectedLanguage,
                                        onOptionSelected = { selectedLanguage = it },
                                        initiallyOpened = languageDialogOpened
                                    )
                                    footer(text = "Compose Unified Settings UI\nVersion 1.2.0")
                                }
                            }
                        }
                    }
                } else {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Profile Details") },
                                navigationIcon = {
                                    IconButton(onClick = { showDetail = false }) {
                                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
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
                            Surface(
                                modifier = Modifier
                                    .sharedBounds(
                                        rememberSharedContentState("demo_profile"),
                                        this@AnimatedContent
                                    )
                                    .size(120.dp),
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.surfaceContainer
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "J",
                                        style = MaterialTheme.typography.displayLarge,
                                        modifier = Modifier.sharedBounds(
                                            rememberSharedContentState("demo_avatar_text"),
                                            this@AnimatedContent
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            Text("John Doe", style = MaterialTheme.typography.headlineMedium)
                            Text("Developer Extraordinaire", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }
}
