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
 * /home/paeelluu/StudioProjects/compose_unified_material_expressive_settings/compose-settings-ui/src/main/java/fr/paeelluu/compose_settings_ui/SettingsItemBase.kt
 *
 * This file is part of the Compose Unified Material Expressive Settings Library.
 * Licensed under the Proprietary License.
 * All rights reserved.
 */

package fr.paeelluu.compose_settings_ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalFoundationApi::class)
@Composable
public fun SettingsItemBase(
    title: String,
    shape: Shape,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    sharedTransitionKey: Any? = null
) {
    val sharedModifier = if (sharedTransitionScope != null && animatedVisibilityScope != null && sharedTransitionKey != null) {
        with(sharedTransitionScope) {
            Modifier.sharedBounds(
                rememberSharedContentState(key = sharedTransitionKey),
                animatedVisibilityScope = animatedVisibilityScope
            )
        }
    } else {
        Modifier
    }

    val alpha = if (enabled) 1f else 0.38f

    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(sharedModifier)
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .then(
                if (onClick != null || onLongClick != null) {
                    Modifier.combinedClickable(
                        enabled = enabled,
                        onClick = onClick ?: {},
                        onLongClick = onLongClick
                    )
                } else Modifier
            )
            .padding(
                horizontal = 24.dp,
                vertical = 20.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (leadingContent != null) {
            Box(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .then(if (!enabled) Modifier.alpha(alpha) else Modifier)
            ) {
                leadingContent()
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        if (trailingContent != null) {
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .then(if (!enabled) Modifier.alpha(alpha) else Modifier)
            ) {
                trailingContent()
            }
        }
    }
}
