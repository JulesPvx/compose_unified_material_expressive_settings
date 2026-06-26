/*
 * e            888                               d8   ,e,
 * d8b      e88~\888 888  888 888-~88e   /~~~8e  _d88__  "   e88~-_
 * /Y88b    d888  888 888  888 888  888       88b  888   888 d888   i
 * /  Y88b   8888  888 888  888 888  888  e88~-888  888   888 8888   |
 * /____Y88b  Y888  888 888  888 888  888 C888  888  888   888 Y888   '
 * /      Y88b  "88_/888 "88_-888 888  888  "88_-888  "88_/ 888  "88_-~
 *
 * Copyright (c) 2026 Jules Pouvreaux
 *
 * This file is part of Adunatio.
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
 */
public fun LazyListScope.settingsSection(
    title: String,
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

    item(key = "section_title_$title") {
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
