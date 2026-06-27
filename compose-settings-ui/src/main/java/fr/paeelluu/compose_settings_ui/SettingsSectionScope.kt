/*
 * '||''''|                                                  ||                       .|'''.|            .     .    ||
 * ||  .    ... ... ... ...  ... ..    ....   ....   ....  ...  .... ...   ....      ||..  '    ....  .||.  .||.  ...  .. ...     ... .  ....
 * ||''|     '|..'   ||'  ||  ||' '' .|...|| ||. '  ||. '   ||   '|.  |  .|...||      ''|||.  .|...||  ||    ||    ||   ||  ||   || ||  ||. '
 * ||         .|.    ||    |  ||     ||      . '|.. . '|..  ||    '|.|   ||         .     '|| ||       ||    ||    ||   ||  ||    |''   . '|..
 * .||.....| .|  ||.  ||...'  .||.     '|...' |'..|' |'..|' .||.    '|     '|...'    |'....|'   '|...'  '|.'  '|.' .||. .||. ||.  '||||. |'..|'
 * ||                                                                                                         .|....'
 * ''''
 *
 * Copyright (c) 2026 Jules Pouvreaux
 *
 * /home/paeelluu/StudioProjects/compose_unified_material_expressive_settings/compose-settings-ui/src/main/java/fr/paeelluu/compose_settings_ui/SettingsSectionScope.kt
 *
 * This file is part of the Compose Unified Material Expressive Settings Library.
 * Licensed under the Proprietary License.
 * All rights reserved.
 */

package fr.paeelluu.compose_settings_ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

/**
 * Receiver scope for [settingsSection] content DSL.
 * This interface defines the DSL for adding various setting items to a section.
 */
public interface SettingsSectionScope {
    /**
     * The [SharedTransitionScope] provided to the section, if any.
     */
    @OptIn(ExperimentalSharedTransitionApi::class)
    public val sharedTransitionScope: SharedTransitionScope?

    /**
     * The [AnimatedVisibilityScope] provided to the section, if any.
     */
    public val animatedVisibilityScope: AnimatedVisibilityScope?

    /**
     * Adds a generic custom item to the section.
     *
     * This is the most basic building block, allowing for complete customization of the item's content.
     * The provided [content] lambda receives the [Shape] that should be applied to the item's background
     * or border to maintain consistency with other items in the section.
     *
     * @param modifier The [Modifier] to be applied to the item.
     * @param visible Whether the item should be visible in the list.
     * @param content The composable content of the item, receiving the recommended [Shape].
     * @category Structure & Layout
     */
    public fun item(
        modifier: Modifier = Modifier,
        visible: Boolean = true,
        content: @Composable (Shape) -> Unit
    )

    /**
     * Adds a clickable action item.
     *
     * Used for primary actions that trigger a side effect or navigate to another screen.
     * Supports a title, optional subtitle, icon, and trailing content.
     *
     * @param title The primary text of the action.
     * @param subtitle Optional secondary text displayed below the title.
     * @param icon Optional composable to be displayed at the start of the item.
     * @param trailingContent Optional composable to be displayed at the end of the item.
     * @param enabled Whether the action can be clicked.
     * @param visible Whether the item should be visible.
     * @param sharedTransitionKey Key used for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the subtitle's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
     * @param onLongClick Optional callback for long press events.
     * @param onClick Callback triggered when the item is clicked.
     * @category Basic Actions
     */
    public fun action(
        title: String,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier,
        onLongClick: (() -> Unit)? = null,
        onClick: () -> Unit
    )

    /**
     * Adds a toggleable switch item.
     *
     * A standard setting item with a title and a [androidx.compose.material3.Switch] at the end.
     *
     * @param title The primary text of the setting.
     * @param checked Current state of the switch.
     * @param onCheckedChange Callback triggered when the switch state changes.
     * @param subtitle Optional secondary text.
     * @param icon Optional leading icon.
     * @param trailingContent Optional additional content before the switch.
     * @param enabled Whether the switch is interactive.
     * @param visible Whether the item should be visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the subtitle's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
     * @param onLongClick Optional long click callback.
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
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier,
        onLongClick: (() -> Unit)? = null
    )

    /**
     * Adds a toggleable checkbox item.
     *
     * A standard setting item with a title and a [androidx.compose.material3.Checkbox] at the end.
     *
     * @param title The primary text of the setting.
     * @param checked Current state of the checkbox.
     * @param onCheckedChange Callback triggered when the checkbox state changes.
     * @param subtitle Optional secondary text.
     * @param icon Optional leading icon.
     * @param trailingContent Optional additional content before the checkbox.
     * @param enabled Whether the checkbox is interactive.
     * @param visible Whether the item should be visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the subtitle's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
     * @param onLongClick Optional long click callback.
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
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier,
        onLongClick: (() -> Unit)? = null
    )

    /**
     * Adds a text input field item.
     *
     * Allows the user to enter text directly within the settings list.
     *
     * @param title The primary label for the text field.
     * @param value The current text value.
     * @param onValueChange Callback triggered when the text changes.
     * @param label Optional floating label text.
     * @param placeholder Optional placeholder text when the field is empty.
     * @param subtitle Optional secondary text below the title.
     * @param icon Optional leading icon.
     * @param trailingContent Optional trailing content.
     * @param enabled Whether the text field is interactive.
     * @param visible Whether the item should be visible.
     * @param isError Whether to display the text field in an error state.
     * @param supportingText Optional text displayed below the field (e.g., error message).
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the subtitle's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
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
        visible: Boolean = true,
        isError: Boolean = false,
        supportingText: String? = null,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier
    )

    /**
     * Adds a navigation link item with a trailing arrow.
     *
     * Visual variant of [action] that typically indicates navigation to a sub-screen.
     *
     * @param title The primary text of the link.
     * @param subtitle Optional secondary text.
     * @param icon Optional leading icon.
     * @param trailingContent Optional trailing content (displayed before the arrow).
     * @param enabled Whether the link is clickable.
     * @param visible Whether the item should be visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the subtitle's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
     * @param onLongClick Optional long click callback.
     * @param onClick Callback triggered when the link is clicked.
     * @category Basic Actions
     */
    public fun link(
        title: String,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier,
        onLongClick: (() -> Unit)? = null,
        onClick: () -> Unit
    )

    /**
     * Adds a horizontal segmented button for mutual exclusion.
     *
     * Allows selecting one option from a small set of choices.
     *
     * @param options The list of available options.
     * @param selectedOption The currently selected option.
     * @param onOptionSelected Callback triggered when a new option is selected.
     * @param displayText Function to convert an option to its display string.
     * @param enabled Whether the segmented button is interactive.
     * @param visible Whether the item should be visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param modifier The [Modifier] to be applied to the item.
     * @category Selection
     */
    public fun <T> segmentedButton(
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String = { it.toString() },
        enabled: Boolean = true,
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier
    )

    /**
     * Adds a numeric stepper with +/- buttons.
     *
     * Allows incrementing or decrementing an integer value within a range.
     *
     * @param title The primary text of the setting.
     * @param value The current numeric value.
     * @param onValueChange Callback triggered when the value changes.
     * @param valueRange The allowed range of values (inclusive).
     * @param subtitle Optional secondary text.
     * @param icon Optional leading icon.
     * @param trailingContent Optional trailing content.
     * @param enabled Whether the stepper is interactive.
     * @param visible Whether the item should be visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the subtitle's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
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
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier
    )

    /**
     * Adds an informational block with a secondary background.
     *
     * Used for highlighting important information or hints.
     *
     * @param text The information text to display.
     * @param icon Optional leading icon.
     * @param visible Whether the information block is visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param textSharedTransitionKey Key used for the text's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
     * @param onLongClick Optional long click callback.
     * @category Structure & Layout
     */
    public fun info(
        text: String,
        icon: (@Composable () -> Unit)? = null,
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        textSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier,
        onLongClick: (() -> Unit)? = null
    )

    /**
     * Adds a specialized user profile item.
     *
     * Displays user details like name, email, and an avatar.
     *
     * @param name The user's name.
     * @param email The user's email address.
     * @param avatar Composable to display the user's avatar.
     * @param trailingContent Optional trailing content.
     * @param enabled Whether the profile item is interactive.
     * @param visible Whether the item should be visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param avatarSharedTransitionKey Key used for the avatar's shared element transition.
     * @param titleSharedTransitionKey Key used for the name's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the email's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
     * @param onLongClick Optional long click callback.
     * @param onClick Optional click callback.
     * @category Basic Actions
     */
    public fun userProfile(
        name: String,
        email: String,
        avatar: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        avatarSharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier,
        onLongClick: (() -> Unit)? = null,
        onClick: (() -> Unit)? = null
    )

    /**
     * Adds a collapsible group of settings.
     *
     * Useful for grouping related settings that can be hidden when not needed.
     *
     * @param title The title of the group.
     * @param icon Optional leading icon for the group header.
     * @param trailingContent Optional trailing content for the group header.
     * @param enabled Whether the group header is interactive.
     * @param visible Whether the group is visible.
     * @param initiallyExpanded Whether the group is initially expanded.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param modifier The [Modifier] to be applied to the group header.
     * @param onLongClick Optional long click callback on the group header.
     * @param content DSL for defining the items within the group.
     * @category Structure & Layout
     */
    public fun expandableGroup(
        title: String,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        visible: Boolean = true,
        initiallyExpanded: Boolean = false,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier,
        onLongClick: (() -> Unit)? = null,
        content: SettingsSectionScope.() -> Unit
    )

    /**
     * Adds an inline expandable selector with radio options.
     *
     * When clicked, it expands to show a list of radio buttons for selection.
     *
     * @param title The title of the selector.
     * @param options The list of available options.
     * @param selectedOption The currently selected option.
     * @param onOptionSelected Callback triggered when a new option is selected.
     * @param displayText Function to convert an option to its display string.
     * @param subtitle Optional secondary text.
     * @param icon Optional leading icon.
     * @param trailingContent Optional trailing content.
     * @param enabled Whether the selector is interactive.
     * @param visible Whether the item should be visible.
     * @param initiallyExpanded Whether the selector is initially expanded.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the subtitle's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
     * @param onLongClick Optional long click callback.
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
        visible: Boolean = true,
        initiallyExpanded: Boolean = false,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier,
        onLongClick: (() -> Unit)? = null
    )

    /**
     * Adds a dialog-based selector for long lists of options.
     *
     * When clicked, it opens a dialog containing the list of options.
     *
     * @param title The title of the selector and dialog.
     * @param options The list of available options.
     * @param selectedOption The currently selected option.
     * @param onOptionSelected Callback triggered when a new option is selected.
     * @param displayText Function to convert an option to its display string.
     * @param subtitle Optional secondary text.
     * @param icon Optional leading icon.
     * @param trailingContent Optional trailing content.
     * @param enabled Whether the selector is interactive.
     * @param visible Whether the item should be visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the subtitle's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
     * @param onLongClick Optional long click callback.
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
        visible: Boolean = true,
        initiallyOpened: Boolean = false,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier,
        onLongClick: (() -> Unit)? = null
    )

    /**
     * Adds a vertical list of radio buttons for mutual exclusion.
     *
     * Directly displays a list of options with radio buttons.
     *
     * @param options The list of available options.
     * @param selectedOption The currently selected option.
     * @param onOptionSelected Callback triggered when a new option is selected.
     * @param displayText Function to convert an option to its display string.
     * @param enabled Whether the radio buttons are interactive.
     * @param visible Whether the group is visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param modifier The [Modifier] to be applied to the group.
     * @category Selection
     */
    public fun <T> radioButtonGroup(
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String = { it.toString() },
        enabled: Boolean = true,
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier
    )

    /**
     * Adds a vertical list of checkboxes for multiple selection.
     *
     * Directly displays a list of options with checkboxes.
     *
     * @param options The list of available options.
     * @param selectedOptions The set of currently selected options.
     * @param onSelectionChange Callback triggered when the selection changes.
     * @param displayText Function to convert an option to its display string.
     * @param enabled Whether the checkboxes are interactive.
     * @param visible Whether the group is visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param modifier The [Modifier] to be applied to the group.
     * @category Selection
     */
    public fun <T> multiSelectList(
        options: List<T>,
        selectedOptions: Set<T>,
        onSelectionChange: (Set<T>) -> Unit,
        displayText: (T) -> String = { it.toString() },
        enabled: Boolean = true,
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier
    )

    /**
     * Adds a range slider for selecting a numeric interval.
     *
     * Allows selecting both a start and end value.
     *
     * @param title The primary text of the setting.
     * @param value The current range selected.
     * @param onValueChange Callback triggered while the range is being changed.
     * @param onValueChangeFinished Callback triggered when the user stops interacting with the slider.
     * @param valueRange The total range of values available.
     * @param steps The number of discrete steps. 0 for a continuous slider.
     * @param valueLabel Function to convert a float value to a display label.
     * @param subtitle Optional secondary text.
     * @param icon Optional leading icon.
     * @param enabled Whether the slider is interactive.
     * @param visible Whether the item should be visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the subtitle's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
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
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier
    )

    /**
     * Adds a Material 3 Time Picker dialog.
     *
     * Opens a dialog for selecting a specific time.
     *
     * @param title The primary text of the setting.
     * @param hour The current hour.
     * @param minute The current minute.
     * @param onTimeSelected Callback triggered when a time is confirmed.
     * @param is24Hour Whether to use 24-hour format.
     * @param subtitle Optional secondary text.
     * @param icon Optional leading icon.
     * @param trailingContent Optional trailing content.
     * @param enabled Whether the item is interactive.
     * @param visible Whether the item should be visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the subtitle's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
     * @param onLongClick Optional long click callback.
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
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier,
        onLongClick: (() -> Unit)? = null
    )

    /**
     * Adds a Material 3 Date Picker dialog.
     *
     * Opens a dialog for selecting a specific date.
     *
     * @param title The primary text of the setting.
     * @param selectedDateMillis The current selected date in milliseconds since epoch.
     * @param onDateSelected Callback triggered when a date is selected.
     * @param subtitle Optional secondary text.
     * @param icon Optional leading icon.
     * @param trailingContent Optional trailing content.
     * @param enabled Whether the item is interactive.
     * @param visible Whether the item should be visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the subtitle's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
     * @param onLongClick Optional long click callback.
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
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier,
        onLongClick: (() -> Unit)? = null
    )

    /**
     * Adds a comprehensive color picker with HSV wheel and presets.
     *
     * Allows selecting a color using a visual picker or from a list of predefined colors.
     *
     * @param title The primary text of the setting.
     * @param selectedColor The currently selected color.
     * @param onColorSelected Callback triggered when a color is selected.
     * @param colors Optional list of preset colors to display.
     * @param subtitle Optional secondary text.
     * @param icon Optional leading icon.
     * @param trailingContent Optional trailing content.
     * @param enabled Whether the item is interactive.
     * @param visible Whether the item should be visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the subtitle's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
     * @param onLongClick Optional long click callback.
     * @category Pickers
     */
    public fun colorPicker(
        title: String,
        selectedColor: Color,
        onColorSelected: (Color) -> Unit,
        colors: List<Color> = emptyList(),
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        trailingContent: (@Composable () -> Unit)? = null,
        enabled: Boolean = true,
        visible: Boolean = true,
        initiallyOpened: Boolean = false,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier,
        onLongClick: (() -> Unit)? = null
    )

    /**
     * Adds small, centered footer text.
     *
     * Typically used for copyright information, version numbers, or legal notices at the end of a section.
     *
     * @param text The footer text content.
     * @param visible Whether the footer is visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param textSharedTransitionKey Key used for the text's shared element transition.
     * @param modifier The [Modifier] to be applied to the footer.
     * @category Structure & Layout
     */
    public fun footer(
        text: String,
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        textSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier
    )

    /**
     * Adds a title/subtitle with a linear progress indicator.
     *
     * Used to indicate an ongoing background process related to the section.
     *
     * @param title The primary text of the loading item.
     * @param subtitle Optional secondary text.
     * @param visible Whether the loading item is visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the subtitle's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
     * @category Structure & Layout
     */
    public fun loading(
        title: String,
        subtitle: String? = null,
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier
    )

    /**
     * Adds a small bold label to sub-divide items.
     *
     * Helps in visually organizing items within a large section.
     *
     * @param text The label text.
     * @param visible Whether the sub-header is visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param textSharedTransitionKey Key used for the text's shared element transition.
     * @param modifier The [Modifier] to be applied to the sub-header.
     * @category Structure & Layout
     */
    public fun subHeader(
        text: String,
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        textSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier
    )

    /**
     * Adds an integrated inline search bar.
     *
     * Allows filtering or searching settings directly within the section.
     *
     * @param query The current search query.
     * @param onQueryChange Callback triggered when the query changes.
     * @param placeholder Placeholder text when the query is empty.
     * @param enabled Whether the search bar is interactive.
     * @param visible Whether the search bar is visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param modifier The [Modifier] to be applied to the search bar.
     * @category Structure & Layout
     */
    public fun searchBar(
        query: String,
        onQueryChange: (String) -> Unit,
        placeholder: String = "Search settings...",
        enabled: Boolean = true,
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier
    )

    /**
     * Adds a list of items to the section based on a count.
     *
     * Similar to [androidx.compose.foundation.lazy.LazyListScope.items].
     *
     * @param count The number of items to add.
     * @param key A factory of stable and unique keys for the items.
     * @param contentType A factory of the content types for the items.
     * @param visible Whether these items should be visible.
     * @param itemContent DSL for defining the content of each item.
     * @category Structure & Layout
     */
    public fun items(
        count: Int,
        key: ((index: Int) -> Any)? = null,
        contentType: (index: Int) -> Any? = { null },
        visible: Boolean = true,
        itemContent: SettingsSectionScope.(Int) -> Unit
    )

    /**
     * Adds a search bar that expands to a full screen search view.
     *
     * @param query The current search query.
     * @param onQueryChange Callback triggered when the query changes.
     * @param onSearch Callback triggered when a search is submitted.
     * @param expanded Whether the search view is currently expanded to full screen.
     * @param onExpandedChange Callback triggered when the expansion state changes.
     * @param placeholder Placeholder text.
     * @param enabled Whether the search bar is interactive.
     * @param visible Whether the search bar is visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param modifier The [Modifier] to be applied to the search bar.
     * @param content The full screen content to display when expanded.
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
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier,
        content: @Composable ColumnScope.() -> Unit
    )

    /**
     * Adds a standard slider for numeric selection.
     *
     * Allows selecting a single float value from a range.
     *
     * @param title The primary text of the setting.
     * @param value The current numeric value.
     * @param onValueChange Callback triggered while the value is being changed.
     * @param onValueChangeFinished Callback triggered when the user stops interacting with the slider.
     * @param valueRange The total range of values available.
     * @param steps The number of discrete steps. 0 for a continuous slider.
     * @param showValue Whether to display the current numeric value.
     * @param valueLabel Function to convert the value to a display label.
     * @param showMinMax Whether to display the minimum and maximum values of the range.
     * @param enablePreciseControls Whether to show +/- buttons for precise adjustments.
     * @param subtitle Optional secondary text.
     * @param icon Optional leading icon.
     * @param enabled Whether the slider is interactive.
     * @param visible Whether the item should be visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param subtitleSharedTransitionKey Key used for the subtitle's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
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
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        subtitleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier
    )

    /**
     * Adds a tag-based keyword editor.
     *
     * Allows the user to add and remove keywords (tags).
     *
     * @param title The title of the setting.
     * @param placeholder Placeholder text for the input field.
     * @param keywords The current list of keywords.
     * @param onAdd Callback triggered when a new keyword is added.
     * @param onRemove Callback triggered when a keyword is removed.
     * @param enabled Whether the keyword editor is interactive.
     * @param visible Whether the item should be visible.
     * @param sharedTransitionKey Key for shared element transitions.
     * @param titleSharedTransitionKey Key used for the title's shared element transition.
     * @param modifier The [Modifier] to be applied to the item.
     * @category Input
     */
    public fun keywordEditor(
        title: String,
        placeholder: String,
        keywords: List<String>,
        onAdd: (String) -> Unit,
        onRemove: (String) -> Unit,
        enabled: Boolean = true,
        visible: Boolean = true,
        sharedTransitionKey: Any? = null,
        titleSharedTransitionKey: Any? = null,
        modifier: Modifier = Modifier
    )
}