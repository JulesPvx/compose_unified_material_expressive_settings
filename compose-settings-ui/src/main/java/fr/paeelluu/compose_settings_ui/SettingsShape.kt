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

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/**
 * Computes the corner radius based on the item's position in the section.
 * Outer corners get 28dp, inner corners get 4dp to create the grouped effect.
 */
public fun computeSettingsShape(index: Int, total: Int): RoundedCornerShape {
    val outer = 28.dp
    val inner = 4.dp

    return when {
        total == 1 -> RoundedCornerShape(outer)
        index == 0 -> RoundedCornerShape(
            topStart = outer, topEnd = outer,
            bottomStart = inner, bottomEnd = inner
        )

        index == total - 1 -> RoundedCornerShape(
            topStart = inner, topEnd = inner,
            bottomStart = outer, bottomEnd = outer
        )

        else -> RoundedCornerShape(inner)
    }
}