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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
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

    public fun <T> selector(
        title: String,
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String = { it.toString() },
        subtitle: String? = null,
        icon: (@Composable () -> Unit)? = null
    )
}

/**
 * Custom Shape that smoothly animates the bottom corners of the header
 * towards a 4.dp radius when expanded, maintaining the top corners.
 */
private class SelectorHeaderShape(
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

private class SelectorOptionsShape(
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

    override fun <T> selector(
        title: String,
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String,
        subtitle: String?,
        icon: (@Composable () -> Unit)?
    ) {
        items.add { shape ->
            var expanded by remember { mutableStateOf(false) }

            val expandedFraction by animateFloatAsState(
                targetValue = if (expanded) 1f else 0f,
                label = "expanded_fraction"
            )

            val arrowRotation = expandedFraction * 180f

            val baseShape = shape as? RoundedCornerShape ?: RoundedCornerShape(4.dp)
            val innerCorner = CornerSize(4.dp)

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                val headerShape = remember(baseShape, expandedFraction) {
                    SelectorHeaderShape(baseShape, expandedFraction)
                }

                val optionsContainerShape = remember(baseShape, expandedFraction) {
                    SelectorOptionsShape(baseShape, expandedFraction)
                }

                SettingsItemBase(
                    title = title,
                    subtitle = subtitle,
                    shape = headerShape,
                    leadingContent = icon,
                    onClick = { expanded = !expanded },
                    trailingContent = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = displayText(selectedOption),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Expand options",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.rotate(arrowRotation)
                            )
                        }
                    }
                )

                AnimatedVisibility(
                    visible = expanded,
                    enter = expandVertically(),
                    exit = shrinkVertically(),
                    modifier = Modifier.clip(optionsContainerShape)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        options.forEachIndexed { index, option ->
                            val isSelected = option == selectedOption
                            val isLast = index == options.lastIndex

                            val optionShape = if (isLast) {
                                RoundedCornerShape(
                                    topStart = innerCorner,
                                    topEnd = innerCorner,
                                    bottomStart = baseShape.bottomStart,
                                    bottomEnd = baseShape.bottomEnd
                                )
                            } else {
                                RoundedCornerShape(innerCorner)
                            }

                            SettingsItemBase(
                                title = displayText(option),
                                shape = optionShape,
                                onClick = {
                                    onOptionSelected(option)
                                    expanded = false
                                },
                                leadingContent = {
                                    Spacer(modifier = Modifier.width(24.dp))
                                },
                                trailingContent = {
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Selected",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
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