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

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape

public interface SettingsSectionScope {
    public fun item(content: @Composable (Shape) -> Unit)

    public fun action(
        title: String,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        onClick: () -> Unit
    )

    public fun switch(
        title: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null
    )

    public fun checkbox(
        title: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null
    )

    public fun textField(
        title: String,
        value: String,
        onValueChange: (String) -> Unit,
        label: String? = null,
        placeholder: String? = null,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null
    )

    public fun link(
        title: String,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null,
        onClick: () -> Unit
    )

    public fun <T> segmentedButton(
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String = { it.toString() }
    )

    public fun stepper(
        title: String,
        value: Int,
        onValueChange: (Int) -> Unit,
        valueRange: IntRange = 0..10,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null
    )

    public fun info(
        text: String,
        icon: (@Composable () -> Unit)? = null
    )

    public fun userProfile(
        name: String,
        email: String,
        avatar: (@Composable () -> Unit)? = null,
        onClick: (() -> Unit)? = null
    )

    public fun expandableGroup(
        title: String,
        icon: (@Composable () -> Unit)? = null,
        content: SettingsSectionScope.() -> Unit
    )

    public fun <T> selector(
        title: String,
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String = { it.toString() },
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null
    )

    public fun slider(
        title: String,
        value: Float,
        onValueChange: (Float) -> Unit,
        valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
        steps: Int = 0,
        showValue: Boolean = true,
        valueLabel: (Float) -> String = { it.toString() },
        showMinMax: Boolean = false,
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null
    )

    public fun keywordEditor(
        title: String,
        placeholder: String,
        keywords: List<String>,
        onAdd: (String) -> Unit,
        onRemove: (String) -> Unit
    )
}
