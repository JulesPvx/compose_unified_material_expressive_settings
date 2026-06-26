/*
 *      e            888                               d8   ,e,
 *     d8b      e88~\888 888  888 888-~88e   /~~~8e  _d88__  "   e88~-_
 *    /Y88b    d888  888 888  888 888  888       88b  888   888 d888   i
 *   /  Y88b   8888  888 888  888 888  888  e88~-888  888   888 8888   |
 *  /____Y88b  Y888  888 888  888 888  888 C888  888  888   888 Y888   '
 * /      Y88b  "88_/888 "88_-888 888  888  "88_-888  "88_/ 888  "88_-~
 *
 * Copyright (c) 2026 Jules Pouvreaux
 *
 * This file is part of Adunatio.
 * Licensed under the Proprietary License.
 * All rights reserved.
 */

package fr.paeelluu.compose_settings_ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

/**
 * Receiver scope for [settingsSection] content DSL.
 */
public interface SettingsSectionScope {
    /**
     * Adds a generic custom item to the section.
     * @category Structure & Layout
     */
    public fun item(content: @Composable (Shape) -> Unit)

    /**
     * Adds a clickable action item.
     * @category Basic Actions
     */
    public fun action(
        title: String,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null,
        onClick: () -> Unit
    )

    /**
     * Adds a toggleable switch item.
     * @category Toggles
     */
    public fun switch(
        title: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a toggleable checkbox item.
     * @category Toggles
     */
    public fun checkbox(
        title: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a text input field item.
     * @category Input
     */
    public fun textField(
        title: String,
        value: String,
        onValueChange: (String) -> Unit,
        label: String? = null,
        placeholder: String? = null,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        isError: Boolean = false,
        supportingText: String? = null,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a navigation link item with a trailing arrow.
     * @category Basic Actions
     */
    public fun link(
        title: String,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null,
        onClick: () -> Unit
    )

    /**
     * Adds a horizontal segmented button for mutual exclusion.
     * @category Selection
     */
    public fun <T> segmentedButton(
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String = { it.toString() },
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a numeric stepper with +/- buttons.
     * @category Input
     */
    public fun stepper(
        title: String,
        value: Int,
        onValueChange: (Int) -> Unit,
        valueRange: IntRange = 0..10,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds an informational block with a secondary background.
     * @category Structure & Layout
     */
    public fun info(
        text: String,
        icon: (@Composable () -> Unit)? = null,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a specialized user profile item.
     * @category Basic Actions
     */
    public fun userProfile(
        name: String,
        email: String,
        avatar: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null,
        onClick: (() -> Unit)? = null
    )

    /**
     * Adds a collapsible group of settings.
     * @category Structure & Layout
     */
    public fun expandableGroup(
        title: String,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null,
        content: SettingsSectionScope.() -> Unit
    )

    /**
     * Adds an inline expandable selector with radio options.
     * @category Selection
     */
    public fun <T> selector(
        title: String,
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String = { it.toString() },
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a dialog-based selector for long lists of options.
     * @category Selection
     */
    public fun <T> dialogSelector(
        title: String,
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String = { it.toString() },
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a vertical list of radio buttons for mutual exclusion.
     * @category Selection
     */
    public fun <T> radioButtonGroup(
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String = { it.toString() },
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a vertical list of checkboxes for multiple selection.
     * @category Selection
     */
    public fun <T> multiSelectList(
        options: List<T>,
        selectedOptions: Set<T>,
        onSelectionChange: (Set<T>) -> Unit,
        displayText: (T) -> String = { it.toString() },
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a range slider for selecting a numeric interval.
     * @category Sliders
     */
    public fun rangeSlider(
        title: String,
        value: ClosedFloatingPointRange<Float>,
        onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
        onValueChangeFinished: (() -> Unit)? = null,
        valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
        steps: Int = 0,
        valueLabel: (Float) -> String = { it.toString() },
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a Material 3 Time Picker dialog.
     * @category Pickers
     */
    public fun timePicker(
        title: String,
        hour: Int,
        minute: Int,
        onTimeSelected: (hour: Int, minute: Int) -> Unit,
        is24Hour: Boolean = true,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a Material 3 Date Picker dialog.
     * @category Pickers
     */
    public fun datePicker(
        title: String,
        selectedDateMillis: Long?,
        onDateSelected: (Long?) -> Unit,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a comprehensive color picker with HSV wheel and presets.
     * @category Pickers
     */
    public fun colorPicker(
        title: String,
        selectedColor: androidx.compose.ui.graphics.Color,
        onColorSelected: (androidx.compose.ui.graphics.Color) -> Unit,
        colors: List<androidx.compose.ui.graphics.Color> = emptyList(),
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds small, centered footer text.
     * @category Structure & Layout
     */
    public fun footer(
        text: String,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a title/subtitle with a linear progress indicator.
     * @category Structure & Layout
     */
    public fun loading(
        title: String,
        subtitle: String? = null,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a small bold label to sub-divide items.
     * @category Structure & Layout
     */
    public fun subHeader(
        text: String,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds an integrated inline search bar.
     * @category Structure & Layout
     */
    public fun searchBar(
        query: String,
        onQueryChange: (String) -> Unit,
        placeholder: String = "Search settings...",
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a list of items to the section.
     * @category Structure & Layout
     */
    public fun items(
        count: Int,
        key: ((index: Int) -> Any)? = null,
        contentType: (index: Int) -> Any? = { null },
        itemContent: SettingsSectionScope.(Int) -> Unit
    )

    /**
     * Adds a search bar that expands to a full screen search view.
     * @category Structure & Layout
     */
    public fun fullScreenSearch(
        query: String,
        onQueryChange: (String) -> Unit,
        onSearch: (String) -> Unit,
        expanded: Boolean,
        onExpandedChange: (Boolean) -> Unit,
        placeholder: String = "Search...",
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null,
        content: @Composable ColumnScope.() -> Unit
    )

    /**
     * Adds a standard slider for numeric selection.
     * @category Sliders
     */
    public fun slider(
        title: String,
        value: Float,
        onValueChange: (Float) -> Unit,
        onValueChangeFinished: (() -> Unit)? = null,
        valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
        steps: Int = 0,
        showValue: Boolean = true,
        valueLabel: (Float) -> String = { it.toString() },
        showMinMax: Boolean = false,
        enablePreciseControls: Boolean = false,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )

    /**
     * Adds a tag-based keyword editor.
     * @category Input
     */
    public fun keywordEditor(
        title: String,
        placeholder: String,
        keywords: List<String>,
        onAdd: (String) -> Unit,
        onRemove: (String) -> Unit,
        enabled: Boolean = true,
        sharedTransitionKey: Any? = null
    )
}
