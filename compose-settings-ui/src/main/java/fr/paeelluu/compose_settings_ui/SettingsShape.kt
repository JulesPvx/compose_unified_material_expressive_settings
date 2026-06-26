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
 * /home/paeelluu/StudioProjects/compose_unified_material_expressive_settings/compose-settings-ui/src/main/java/fr/paeelluu/compose_settings_ui/SettingsShape.kt
 *
 * This file is part of the Compose Unified Material Expressive Settings Library.
 * Licensed under the Proprietary License.
 * All rights reserved.
 */

package fr.paeelluu.compose_settings_ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

internal class SelectorHeaderShape(
    private val baseShape: RoundedCornerShape,
    private val fraction: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val innerCornerPx = with(density) { 4.dp.toPx() }

        val topStart = baseShape.topStart.toPx(size, density)
        val topEnd = baseShape.topEnd.toPx(size, density)
        val originalBottomStart = baseShape.bottomStart.toPx(size, density)
        val originalBottomEnd = baseShape.bottomEnd.toPx(size, density)

        val currentBottomStart = originalBottomStart + (innerCornerPx - originalBottomStart) * fraction
        val currentBottomEnd = originalBottomEnd + (innerCornerPx - originalBottomEnd) * fraction

        return Outline.Rounded(
            RoundRect(
                rect = Rect(0f, 0f, size.width, size.height),
                topLeft = CornerRadius(topStart, topStart),
                topRight = CornerRadius(topEnd, topEnd),
                bottomRight = CornerRadius(currentBottomEnd, currentBottomEnd),
                bottomLeft = CornerRadius(currentBottomStart, currentBottomStart)
            )
        )
    }
}

internal class SelectorOptionsShape(
    private val baseShape: RoundedCornerShape,
    private val fraction: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val innerCornerPx = with(density) { 4.dp.toPx() }

        val originalBottomStart = baseShape.bottomStart.toPx(size, density)
        val originalBottomEnd = baseShape.bottomEnd.toPx(size, density)

        val currentTopStart = originalBottomStart + (innerCornerPx - originalBottomStart) * fraction
        val currentTopEnd = originalBottomEnd + (innerCornerPx - originalBottomEnd) * fraction

        return Outline.Rounded(
            RoundRect(
                rect = Rect(0f, 0f, size.width, size.height),
                topLeft = CornerRadius(currentTopStart, currentTopStart),
                topRight = CornerRadius(currentTopEnd, currentTopEnd),
                bottomRight = CornerRadius(originalBottomEnd, originalBottomEnd),
                bottomLeft = CornerRadius(originalBottomStart, originalBottomStart)
            )
        )
    }
}

internal fun computeSettingsShape(index: Int, total: Int): RoundedCornerShape {
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