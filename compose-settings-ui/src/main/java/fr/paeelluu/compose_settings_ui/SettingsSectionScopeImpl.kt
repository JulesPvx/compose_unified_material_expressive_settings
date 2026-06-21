package fr.paeelluu.compose_settings_ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.paeelluu.compose_settings_ui.components.KeywordEditor

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

    override fun checkbox(
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
                trailingContent = { Checkbox(checked = checked, onCheckedChange = onCheckedChange) }
            )
        }
    }

    override fun textField(
        title: String,
        value: String,
        onValueChange: (String) -> Unit,
        label: String?,
        placeholder: String?,
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
                            Box(modifier = Modifier.padding(end = 16.dp)) {
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
                    }
                    OutlinedTextField(
                        value = value,
                        onValueChange = onValueChange,
                        label = label?.let { { Text(it) } },
                        placeholder = placeholder?.let { { Text(it) } },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }
            }
        }
    }

    override fun link(
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
                onClick = onClick,
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }
    }

    @OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3ExpressiveApi::class)
    override fun <T> segmentedButton(
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String
    ) {
        items.add { shape ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape),
                color = MaterialTheme.colorScheme.surfaceContainer
            ) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        ButtonGroupDefaults.ConnectedSpaceBetween,
                        Alignment.CenterHorizontally
                    ),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    options.forEachIndexed { index, option ->
                        ToggleButton(
                            checked = option == selectedOption,
                            onCheckedChange = { onOptionSelected(option) },
                            shapes = when (index) {
                                0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                options.size - 1 -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                            },
                            modifier = Modifier
                                .semantics { role = Role.RadioButton }
                                .weight(1f),
                        ) {
                            Text(
                                text = displayText(option),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                }
            }
        }
    }

    override fun stepper(
        title: String,
        value: Int,
        onValueChange: (Int) -> Unit,
        valueRange: IntRange,
        subtitle: String?,
        icon: (@Composable () -> Unit)?
    ) {
        items.add { shape ->
            SettingsItemBase(
                title = title,
                subtitle = subtitle,
                shape = shape,
                leadingContent = icon,
                trailingContent = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = { if (value > valueRange.first) onValueChange(value - 1) },
                            enabled = value > valueRange.first
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Decrease")
                        }
                        Text(
                            text = value.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.widthIn(min = 24.dp),
                            textAlign = TextAlign.Center
                        )
                        IconButton(
                            onClick = { if (value < valueRange.last) onValueChange(value + 1) },
                            enabled = value < valueRange.last
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Increase")
                        }
                    }
                }
            )
        }
    }

    override fun info(text: String, icon: (@Composable () -> Unit)?) {
        items.add { shape ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (icon != null) {
                        icon()
                    } else {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }

    override fun userProfile(
        name: String,
        email: String,
        avatar: (@Composable () -> Unit)?,
        onClick: (() -> Unit)?
    ) {
        items.add { shape ->
            SettingsItemBase(
                title = name,
                subtitle = email,
                shape = shape,
                onClick = onClick,
                leadingContent = avatar ?: {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = name.take(1).uppercase(),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            )
        }
    }

    override fun expandableGroup(
        title: String,
        icon: (@Composable () -> Unit)?,
        content: SettingsSectionScope.() -> Unit
    ) {
        items.add { shape ->
            var expanded by remember { mutableStateOf(false) }
            val expandedFraction by animateFloatAsState(
                targetValue = if (expanded) 1f else 0f,
                label = "expanded_fraction"
            )

            val baseShape = shape as? RoundedCornerShape ?: RoundedCornerShape(4.dp)

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                val headerShape = remember(baseShape, expandedFraction) {
                    SelectorHeaderShape(baseShape, expandedFraction)
                }

                SettingsItemBase(
                    title = title,
                    shape = headerShape,
                    leadingContent = icon,
                    onClick = { expanded = !expanded },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.rotate(expandedFraction * 180f)
                        )
                    }
                )

                AnimatedVisibility(
                    visible = expanded,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    val innerScope = SettingsSectionScopeImpl().apply(content)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        innerScope.items.forEachIndexed { index, itemContent ->
                            val isLast = index == innerScope.items.lastIndex
                            val innerCorner = CornerSize(4.dp)

                            val itemShape = if (isLast) {
                                RoundedCornerShape(
                                    topStart = innerCorner,
                                    topEnd = innerCorner,
                                    bottomStart = baseShape.bottomStart,
                                    bottomEnd = baseShape.bottomEnd
                                )
                            } else {
                                RoundedCornerShape(innerCorner)
                            }

                            itemContent(itemShape)
                        }
                    }
                }
            }
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
                            Box(modifier = Modifier.padding(end = 16.dp)) {
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

    override fun keywordEditor(
        title: String,
        placeholder: String,
        keywords: List<String>,
        onAdd: (String) -> Unit,
        onRemove: (String) -> Unit
    ) {
        items.add { shape ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape),
                color = MaterialTheme.colorScheme.surfaceContainer
            ) {
                KeywordEditor(
                    title = title,
                    placeholder = placeholder,
                    keywords = keywords,
                    onAdd = onAdd,
                    onRemove = onRemove,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        }
    }
}