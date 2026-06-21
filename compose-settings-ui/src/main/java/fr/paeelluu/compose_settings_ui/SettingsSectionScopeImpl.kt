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
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

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

    override fun slider(
        title: String,
        value: Float,
        onValueChange: (Float) -> Unit,
        valueRange: ClosedFloatingPointRange<Float>,
        steps: Int,
        showValue: Boolean,
        valueLabel: (Float) -> String,
        showMinMax: Boolean,
        subtitle: String?,
        icon: (@Composable () -> Unit)?
    ) {
        items.add { shape ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape),
                color = MaterialTheme.colorScheme.surfaceContainer
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        if (icon != null) {
                            androidx.compose.foundation.layout.Box(modifier = Modifier.padding(end = 16.dp)) {
                                icon()
                            }
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (subtitle != null) {
                                Text(
                                    text = subtitle,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        if (showValue) {
                            Text(
                                text = valueLabel(value),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }

                    Slider(
                        value = value,
                        onValueChange = onValueChange,
                        valueRange = valueRange,
                        steps = steps,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )

                    if (showMinMax) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = valueLabel(valueRange.start),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = valueLabel(valueRange.endInclusive),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}