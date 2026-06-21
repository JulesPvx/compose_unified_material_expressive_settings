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