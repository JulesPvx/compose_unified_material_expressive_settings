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
 * /home/paeelluu/StudioProjects/compose_unified_material_expressive_settings/compose-settings-ui/src/main/java/fr/paeelluu/compose_settings_ui/SettingsSection.kt
 *
 * This file is part of the Compose Unified Material Expressive Settings Library.
 * Licensed under the Proprietary License.
 * All rights reserved.
 */

package fr.paeelluu.compose_settings_ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Extension on [LazyListScope] to add a settings section with a title and grouped items.
 * @param title The title of the section.
 * @param enabled Whether the section is enabled.
 * @param sharedTransitionScope The shared transition scope for the section.
 * @param animatedVisibilityScope The animated visibility scope for the section.
 * @param content The DSL for defining the items within the section.
 */
public fun LazyListScope.settingsSection(
    title: String? = null,
    enabled: Boolean = true,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    content: SettingsSectionScope.() -> Unit
) {
    val scope = SettingsSectionScopeImpl(
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        sectionEnabled = enabled
    )
    scope.content()

    val titleAlpha = if (enabled) 1f else 0.38f

    if (title != null) {
        item(key = "section_title_${title}") {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary.copy(alpha = titleAlpha),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp, bottom = 8.dp)
            )
        }
    }

    itemsIndexed(
        items = scope.items,
        key = { index, _ -> "section_item_${title}_$index" }
    ) { index, itemContent ->
        Box(modifier = Modifier.padding(bottom = 2.dp)) {
            val shape = computeSettingsShape(index = index, total = scope.items.size)
            itemContent(shape)
        }
    }

    item(key = "section_spacer_$title") {
        Spacer(modifier = Modifier.height(8.dp))
    }
}
