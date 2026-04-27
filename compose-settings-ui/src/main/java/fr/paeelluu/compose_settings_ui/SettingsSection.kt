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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

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
}

internal class SettingsSectionScopeImpl : SettingsSectionScope {
    internal val items = mutableListOf<@Composable (Shape) -> Unit>()

    override fun item(content: @Composable (Shape) -> Unit) {
        items.add(content)
    }

    override fun action(
        title: String,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        onClick: () -> Unit
    ) {
        items.add { shape ->
            SettingsItemBase(
                title = title,
                subtitle = subtitle,
                shape = shape,
                leadingContent = icon,
                onClick = onClick
            )
        }
    }

    override fun switch(
        title: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        subtitle: String?,
        icon: (@Composable () -> Unit)?
    ) {
        items.add { shape ->
            SettingsItemBase(
                title = title,
                subtitle = subtitle,
                shape = shape,
                leadingContent = icon,
                onClick = { onCheckedChange(!checked) },
                trailingContent = { Switch(checked = checked, onCheckedChange = onCheckedChange) }
            )
        }
    }
}

@Composable
public fun SettingsSection(
    title: String,
    modifier: Modifier = Modifier,
    content: SettingsSectionScope.() -> Unit
) {
    val scope = SettingsSectionScopeImpl().apply(content)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            scope.items.forEachIndexed { index, itemContent ->
                val shape = computeSettingsShape(index = index, total = scope.items.size)
                itemContent(shape)
            }
        }
    }
}