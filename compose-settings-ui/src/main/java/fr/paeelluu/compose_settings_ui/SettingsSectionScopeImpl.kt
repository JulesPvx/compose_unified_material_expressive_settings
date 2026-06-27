/*
 * '||''''|                                                  ||                       .|'''.|            .     .    ||
 * ||  .    ... ... ... ...  ... ..    ....   ....   ....  ...  .... ...   ....      ||..  '    ....  .||.  .||.  ...  .. ...     ... .  ....
 * ||''|     '|..'   ||'  ||  ||' '' .|...|| ||. '  ||. '   ||   '|.  |  .|...||      ''|||.  .|...||  ||    ||    ||   ||  ||   || ||  ||. '
 * ||         .|.    ||    |  ||     ||      . '|.. . '|..  ||    '|.|   ||         .     '|| ||       ||    ||    ||   ||  ||    |''   . '|..
 * .||.....| .|  ||.  ||...'  .||.     '|...' |'..|' |'..|' .||.    '|     '|...'    |'....|'   '|...'  '|.'  '|.' .||. .||. ||.  '||||. |'..|'
 * ||                                                                                                         .|....'
 * ''''
 *
 * Copyright (c) 2026 Jules Pouvreaux
 *
 * /home/paeelluu/StudioProjects/compose_unified_material_expressive_settings/compose-settings-ui/src/main/java/fr/paeelluu/compose_settings_ui/SettingsSectionScopeImpl.kt
 *
 * This file is part of the Compose Unified Material Expressive Settings Library.
 * Licensed under the Proprietary License.
 * All rights reserved.
 */

package fr.paeelluu.compose_settings_ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TimeToLeave
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import fr.paeelluu.compose_settings_ui.components.KeywordEditor
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
internal class SettingsSectionScopeImpl(
    override val sharedTransitionScope: SharedTransitionScope? = null,
    override val animatedVisibilityScope: AnimatedVisibilityScope? = null,
    private val sectionEnabled: Boolean = true
) : SettingsSectionScope {
    internal val items = mutableListOf<@Composable (Shape) -> Unit>()

    override fun item(modifier: Modifier, visible: Boolean, content: @Composable (Shape) -> Unit) {
        if (visible) {
            items.add { shape ->
                Box(modifier = modifier.clip(shape)) {
                    content(shape)
                }
            }
        }
    }

    override fun action(
        title: String,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        trailingContent: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier,
        onLongClick: (() -> Unit)?,
        onClick: () -> Unit
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val contentAlpha = if (isEnabled) 1f else 0.38f
                SettingsItemBase(
                    title = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                            modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                        )
                    },
                    subtitle = subtitle?.let {
                        {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha),
                                modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                            )
                        }
                    },
                    shape = shape,
                    modifier = modifier,
                    leadingContent = icon,
                    onClick = onClick,
                    onLongClick = onLongClick,
                    enabled = isEnabled,
                    trailingContent = trailingContent,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedTransitionKey = sharedTransitionKey
                )
            }
        }
    }

    override fun switch(
        title: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        trailingContent: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier,
        onLongClick: (() -> Unit)?
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val contentAlpha = if (isEnabled) 1f else 0.38f
                SettingsItemBase(
                    title = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                            modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                        )
                    },
                    subtitle = subtitle?.let {
                        {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha),
                                modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                            )
                        }
                    },
                    shape = shape,
                    modifier = modifier,
                    leadingContent = icon,
                    onClick = { onCheckedChange(!checked) },
                    onLongClick = onLongClick,
                    enabled = isEnabled,
                    trailingContent = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            trailingContent?.invoke()
                            Switch(checked = checked, onCheckedChange = onCheckedChange, enabled = isEnabled)
                        }
                    },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedTransitionKey = sharedTransitionKey
                )
            }
        }
    }

    override fun checkbox(
        title: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        trailingContent: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier,
        onLongClick: (() -> Unit)?
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val contentAlpha = if (isEnabled) 1f else 0.38f
                SettingsItemBase(
                    title = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                            modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                        )
                    },
                    subtitle = subtitle?.let {
                        {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha),
                                modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                            )
                        }
                    },
                    shape = shape,
                    modifier = modifier,
                    leadingContent = icon,
                    onClick = { onCheckedChange(!checked) },
                    onLongClick = onLongClick,
                    enabled = isEnabled,
                    trailingContent = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            trailingContent?.invoke()
                            Checkbox(checked = checked, onCheckedChange = onCheckedChange, enabled = isEnabled)
                        }
                    },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedTransitionKey = sharedTransitionKey
                )
            }
        }
    }

    override fun textField(
        title: String,
        value: String,
        onValueChange: (String) -> Unit,
        label: String?,
        placeholder: String?,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        trailingContent: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        isError: Boolean,
        supportingText: String?,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val alpha = if (isEnabled) 1f else 0.38f

                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .sharedBoundsModifier(sharedTransitionKey)
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
                                Box(
                                    modifier = Modifier
                                        .padding(end = 16.dp)
                                        .then(if (!isEnabled) Modifier.alpha(alpha) else Modifier)
                                ) {
                                    icon()
                                }
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha),
                                    modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                                )
                                if (subtitle != null) {
                                    Text(
                                        text = subtitle,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha),
                                        modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                                    )
                                }
                            }
                            if (trailingContent != null) {
                                Box(modifier = Modifier.then(if (!isEnabled) Modifier.alpha(alpha) else Modifier)) {
                                    trailingContent()
                                }
                            }
                        }
                        OutlinedTextField(
                            value = value,
                            onValueChange = onValueChange,
                            label = label?.let { { Text(it) } },
                            placeholder = placeholder?.let { { Text(it) } },
                            isError = isError,
                            enabled = isEnabled,
                            supportingText = supportingText?.let { { Text(it) } },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }

    override fun link(
        title: String,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        trailingContent: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier,
        onLongClick: (() -> Unit)?,
        onClick: () -> Unit
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val contentAlpha = if (isEnabled) 1f else 0.38f
                SettingsItemBase(
                    title = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                            modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                        )
                    },
                    subtitle = subtitle?.let {
                        {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha),
                                modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                            )
                        }
                    },
                    shape = shape,
                    modifier = modifier,
                    leadingContent = icon,
                    onClick = onClick,
                    onLongClick = onLongClick,
                    enabled = isEnabled,
                    trailingContent = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            trailingContent?.invoke()
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedTransitionKey = sharedTransitionKey
                )
            }
        }
    }

    @OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3ExpressiveApi::class)
    override fun <T> segmentedButton(
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        modifier: Modifier
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled

                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .sharedBoundsModifier(sharedTransitionKey)
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
                                enabled = isEnabled,
                                shapes = when (index) {
                                    0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                                    options.size - 1 -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                                    else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                                },
                                colors = ToggleButtonDefaults.toggleButtonColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    checkedContainerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onSurface,
                                    checkedContentColor = MaterialTheme.colorScheme.onPrimary,
                                    disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.12f),
                                    disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                                ),
                                modifier = Modifier
                                    .semantics { role = Role.RadioButton }
                                    .fillMaxRowHeight()
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
    }

    override fun stepper(
        title: String,
        value: Int,
        onValueChange: (Int) -> Unit,
        valueRange: IntRange,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        trailingContent: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val contentAlpha = if (isEnabled) 1f else 0.38f
                SettingsItemBase(
                    title = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                            modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                        )
                    },
                    subtitle = subtitle?.let {
                        {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha),
                                modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                            )
                        }
                    },
                    shape = shape,
                    modifier = modifier,
                    leadingContent = icon,
                    enabled = isEnabled,
                    trailingContent = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            trailingContent?.invoke()
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                IconButton(
                                    onClick = { if (value > valueRange.first) onValueChange(value - 1) },
                                    enabled = isEnabled && value > valueRange.first
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
                                    enabled = isEnabled && value < valueRange.last
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Increase")
                                }
                            }
                        }
                    },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedTransitionKey = sharedTransitionKey
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun info(
        text: String,
        icon: (@Composable () -> Unit)?,
        visible: Boolean,
        sharedTransitionKey: Any?,
        textSharedTransitionKey: Any?,
        modifier: Modifier,
        onLongClick: (() -> Unit)?
    ) {
        if (visible) {
            items.add { shape ->
                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .sharedBoundsModifier(sharedTransitionKey)
                        .clip(shape)
                        .then(
                            if (onLongClick != null) {
                                Modifier.combinedClickable(
                                    onClick = {},
                                    onLongClick = onLongClick
                                )
                            } else Modifier
                        ),
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
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.sharedElementModifier(textSharedTransitionKey)
                        )
                    }
                }
            }
        }
    }

    override fun userProfile(
        name: String,
        email: String,
        avatar: (@Composable () -> Unit)?,
        trailingContent: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        avatarSharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier,
        onLongClick: (() -> Unit)?,
        onClick: (() -> Unit)?
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val contentAlpha = if (isEnabled) 1f else 0.38f
                SettingsItemBase(
                    title = {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                            modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                        )
                    },
                    subtitle = {
                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha),
                            modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                        )
                    },
                    shape = shape,
                    modifier = modifier,
                    onClick = onClick,
                    onLongClick = onLongClick,
                    enabled = isEnabled,
                    leadingContent = {
                        Box(modifier = Modifier.sharedElementModifier(avatarSharedTransitionKey)) {
                            avatar?.invoke() ?: Surface(
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
                    },
                    trailingContent = trailingContent,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedTransitionKey = sharedTransitionKey
                )
            }
        }
    }

    override fun expandableGroup(
        title: String,
        icon: (@Composable () -> Unit)?,
        trailingContent: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        initiallyExpanded: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        modifier: Modifier,
        onLongClick: (() -> Unit)?,
        content: SettingsSectionScope.() -> Unit
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val contentAlpha = if (isEnabled) 1f else 0.38f
                var expanded by remember { mutableStateOf(initiallyExpanded) }
                LaunchedEffect(initiallyExpanded) {
                    expanded = initiallyExpanded
                }
                val expandedFraction by animateFloatAsState(
                    targetValue = if (expanded) 1f else 0f,
                    label = "expanded_fraction"
                )

                val baseShape = shape as? RoundedCornerShape ?: RoundedCornerShape(4.dp)

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    val headerShape = remember(baseShape, expandedFraction) {
                        SelectorHeaderShape(baseShape, expandedFraction)
                    }

                    SettingsItemBase(
                        title = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                                modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                            )
                        },
                        shape = headerShape,
                        leadingContent = icon,
                        onClick = { expanded = !expanded },
                        onLongClick = onLongClick,
                        enabled = isEnabled,
                        trailingContent = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                trailingContent?.invoke()
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    modifier = Modifier.rotate(expandedFraction * 180f)
                                )
                            }
                        },
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                        sharedTransitionKey = sharedTransitionKey
                    )

                    AnimatedVisibility(
                        visible = expanded,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        val innerScope = SettingsSectionScopeImpl(
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope,
                            sectionEnabled = isEnabled
                        )
                        innerScope.content()

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
    }

    override fun <T> selector(
        title: String,
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        trailingContent: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        initiallyExpanded: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier,
        onLongClick: (() -> Unit)?
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val contentAlpha = if (isEnabled) 1f else 0.38f
                var expanded by remember { mutableStateOf(initiallyExpanded) }
                LaunchedEffect(initiallyExpanded) {
                    expanded = initiallyExpanded
                }

                val expandedFraction by animateFloatAsState(
                    targetValue = if (expanded) 1f else 0f,
                    label = "expanded_fraction"
                )

                val arrowRotation = expandedFraction * 180f

                val baseShape = shape as? RoundedCornerShape ?: RoundedCornerShape(4.dp)
                val innerCorner = CornerSize(4.dp)

                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    val headerShape = remember(baseShape, expandedFraction) {
                        SelectorHeaderShape(baseShape, expandedFraction)
                    }

                    val optionsContainerShape = remember(baseShape, expandedFraction) {
                        SelectorOptionsShape(baseShape, expandedFraction)
                    }

                    SettingsItemBase(
                        title = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                                modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                            )
                        },
                        subtitle = subtitle?.let {
                            {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha),
                                    modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                                )
                            }
                        },
                        shape = headerShape,
                        leadingContent = icon,
                        onClick = { expanded = !expanded },
                        onLongClick = onLongClick,
                        enabled = isEnabled,
                        trailingContent = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                trailingContent?.invoke()
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = displayText(selectedOption),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (isEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
                                        textAlign = TextAlign.Center
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Expand options",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.rotate(arrowRotation)
                                    )
                                }
                            }
                        },
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                        sharedTransitionKey = sharedTransitionKey
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
                                    title = {
                                        Text(
                                            text = displayText(option),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (isEnabled) 1f else 0.38f)
                                        )
                                    },
                                    shape = optionShape,
                                    onClick = {
                                        onOptionSelected(option)
                                        expanded = false
                                    },
                                    enabled = isEnabled,
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

    @OptIn(ExperimentalMaterial3Api::class)
    override fun <T> dialogSelector(
        title: String,
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        trailingContent: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        initiallyOpened: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier,
        onLongClick: (() -> Unit)?
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val contentAlpha = if (isEnabled) 1f else 0.38f
                var showDialog by remember { mutableStateOf(initiallyOpened) }
                LaunchedEffect(initiallyOpened) {
                    showDialog = initiallyOpened
                }

                SettingsItemBase(
                    title = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                            modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                        )
                    },
                    subtitle = {
                        Text(
                            text = subtitle ?: displayText(selectedOption),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha),
                            modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                        )
                    },
                    shape = shape,
                    modifier = modifier,
                    leadingContent = icon,
                    onClick = { showDialog = true },
                    onLongClick = onLongClick,
                    enabled = isEnabled,
                    trailingContent = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            trailingContent?.invoke()
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedTransitionKey = sharedTransitionKey
                )

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(text = title) },
                        text = {
                            LazyColumn {
                                items(options) { option ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                onOptionSelected(option)
                                                showDialog = false
                                            }
                                            .padding(vertical = 12.dp, horizontal = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = option == selectedOption,
                                            onClick = null
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(
                                            text = displayText(option),
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            TextButton(onClick = { showDialog = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }
        }
    }

    override fun <T> radioButtonGroup(
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        modifier: Modifier
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val alpha = if (isEnabled) 1f else 0.38f

                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .sharedBoundsModifier(sharedTransitionKey)
                        .clip(shape),
                    color = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        options.forEach { option ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(enabled = isEnabled) { onOptionSelected(option) }
                                    .padding(horizontal = 24.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = option == selectedOption,
                                    onClick = null,
                                    enabled = isEnabled
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = displayText(option),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun <T> multiSelectList(
        options: List<T>,
        selectedOptions: Set<T>,
        onSelectionChange: (Set<T>) -> Unit,
        displayText: (T) -> String,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        modifier: Modifier
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val alpha = if (isEnabled) 1f else 0.38f

                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .sharedBoundsModifier(sharedTransitionKey)
                        .clip(shape),
                    color = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        options.forEach { option ->
                            val isChecked = selectedOptions.contains(option)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(enabled = isEnabled) {
                                        val newSelection = if (isChecked) {
                                            selectedOptions - option
                                        } else {
                                            selectedOptions + option
                                        }
                                        onSelectionChange(newSelection)
                                    }
                                    .padding(horizontal = 24.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = null,
                                    enabled = isEnabled
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = displayText(option),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun rangeSlider(
        title: String,
        value: ClosedFloatingPointRange<Float>,
        onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
        onValueChangeFinished: (() -> Unit)?,
        valueRange: ClosedFloatingPointRange<Float>,
        steps: Int,
        valueLabel: (Float) -> String,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val alpha = if (isEnabled) 1f else 0.38f

                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .sharedBoundsModifier(sharedTransitionKey)
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
                                Box(
                                    modifier = Modifier
                                        .padding(end = 16.dp)
                                        .then(if (!isEnabled) Modifier.alpha(alpha) else Modifier)
                                ) {
                                    icon()
                                }
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha),
                                    modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                                )
                                if (subtitle != null) {
                                    Text(
                                        text = subtitle,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha),
                                        modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                                    )
                                }
                            }
                            Text(
                                text = "${valueLabel(value.start)} - ${valueLabel(value.endInclusive)}",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = alpha),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }

                        RangeSlider(
                            value = value,
                            onValueChange = onValueChange,
                            onValueChangeFinished = onValueChangeFinished,
                            valueRange = valueRange,
                            steps = steps,
                            enabled = isEnabled,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun timePicker(
        title: String,
        hour: Int,
        minute: Int,
        onTimeSelected: (hour: Int, minute: Int) -> Unit,
        is24Hour: Boolean,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        trailingContent: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier,
        onLongClick: (() -> Unit)?
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val contentAlpha = if (isEnabled) 1f else 0.38f
                var showDialog by remember { mutableStateOf(false) }
                val timePickerState = rememberTimePickerState(
                    initialHour = hour,
                    initialMinute = minute,
                    is24Hour = is24Hour
                )

                SettingsItemBase(
                    title = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                            modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                        )
                    },
                    subtitle = {
                        Text(
                            text = subtitle ?: String.format(java.util.Locale.US, "%02d:%02d", hour, minute),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha),
                            modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                        )
                    },
                    shape = shape,
                    modifier = modifier,
                    leadingContent = icon,
                    onClick = { showDialog = true },
                    onLongClick = onLongClick,
                    enabled = isEnabled,
                    trailingContent = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            trailingContent?.invoke()
                            Icon(Icons.Default.TimeToLeave, contentDescription = null)
                        }
                    },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedTransitionKey = sharedTransitionKey
                )

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        confirmButton = {
                            TextButton(onClick = {
                                onTimeSelected(timePickerState.hour, timePickerState.minute)
                                showDialog = false
                            }) { Text("OK") }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDialog = false }) { Text("Cancel") }
                        },
                        title = { Text(title) },
                        text = { TimePicker(state = timePickerState) }
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun datePicker(
        title: String,
        selectedDateMillis: Long?,
        onDateSelected: (Long?) -> Unit,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        trailingContent: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier,
        onLongClick: (() -> Unit)?
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val contentAlpha = if (isEnabled) 1f else 0.38f
                var showDialog by remember { mutableStateOf(false) }
                val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDateMillis)
                val formatter = remember { java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()) }

                SettingsItemBase(
                    title = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                            modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                        )
                    },
                    subtitle = {
                        Text(
                            text = subtitle ?: selectedDateMillis?.let { formatter.format(java.util.Date(it)) } ?: "Select date",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha),
                            modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                        )
                    },
                    shape = shape,
                    modifier = modifier,
                    leadingContent = icon,
                    onClick = { showDialog = true },
                    onLongClick = onLongClick,
                    enabled = isEnabled,
                    trailingContent = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            trailingContent?.invoke()
                            Icon(Icons.Default.DateRange, contentDescription = null)
                        }
                    },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedTransitionKey = sharedTransitionKey
                )

                if (showDialog) {
                    DatePickerDialog(
                        onDismissRequest = { showDialog = false },
                        confirmButton = {
                            TextButton(onClick = {
                                onDateSelected(datePickerState.selectedDateMillis)
                                showDialog = false
                            }) { Text("OK") }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDialog = false }) { Text("Cancel") }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun colorPicker(
        title: String,
        selectedColor: Color,
        onColorSelected: (Color) -> Unit,
        colors: List<Color>,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        trailingContent: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        initiallyOpened: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier,
        onLongClick: (() -> Unit)?
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val contentAlpha = if (isEnabled) 1f else 0.38f
                var showSheet by remember { mutableStateOf(initiallyOpened) }
                LaunchedEffect(initiallyOpened) {
                    showSheet = initiallyOpened
                }
                val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

                val defaultColors = remember {
                    listOf(
                        Color(0xFFF44336), Color(0xFFE91E63), Color(0xFF9C27B0), Color(0xFF673AB7),
                        Color(0xFF3F51B5), Color(0xFF2196F3), Color(0xFF03A9F4), Color(0xFF00BCD4),
                        Color(0xFF009688), Color(0xFF4CAF50), Color(0xFF8BC34A), Color(0xFFCDDC39),
                        Color(0xFF6E7FDC), Color(0xFFFFC107), Color(0xFFFF9800), Color(0xFFFF5722)
                    )
                }
                val displayColors = colors.ifEmpty { defaultColors }

                SettingsItemBase(
                    title = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                            modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                        )
                    },
                    subtitle = subtitle?.let {
                        {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = contentAlpha),
                                modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                            )
                        }
                    },
                    shape = shape,
                    modifier = modifier,
                    leadingContent = icon,
                    onClick = { showSheet = true },
                    onLongClick = onLongClick,
                    enabled = isEnabled,
                    trailingContent = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            trailingContent?.invoke()
                            Surface(
                                modifier = Modifier.size(28.dp),
                                shape = CircleShape,
                                color = selectedColor,
                                border = BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant)
                            ) {}
                        }
                    },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedTransitionKey = sharedTransitionKey
                )

                if (showSheet) {
                    ModalBottomSheet(
                        onDismissRequest = { showSheet = false },
                        sheetState = sheetState,
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    ) {
                        val colorController = rememberColorPickerController()
                        var hexText by remember { mutableStateOf(longToHex(selectedColor.toArgb().toLong() and 0xFFFFFFFFL)) }
                        var isHexError by remember { mutableStateOf(false) }

                        LaunchedEffect(selectedColor) {
                            val newHex = longToHex(selectedColor.toArgb().toLong() and 0xFFFFFFFFL)
                            if (hexText != newHex) {
                                hexText = newHex
                                isHexError = false
                            }
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp)
                                .padding(bottom = 48.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(selectedColor)
                                        .border(
                                            1.dp,
                                            MaterialTheme.colorScheme.outlineVariant,
                                            RoundedCornerShape(12.dp)
                                        )
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                OutlinedTextField(
                                    value = hexText,
                                    onValueChange = { input: String ->
                                        val cleanInput = if (input.startsWith("#")) input else "#$input"
                                        if (cleanInput.length <= 9) {
                                            hexText = cleanInput.uppercase()
                                            val parsedColor = hexToLong(hexText)
                                            if (parsedColor != null) {
                                                isHexError = false
                                                onColorSelected(Color(parsedColor))
                                            } else {
                                                isHexError = hexText.length >= 7
                                            }
                                        }
                                    },
                                    label = { Text("Hex Color") },
                                    isError = isHexError,
                                    singleLine = true,
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }

                            HsvColorPicker(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp)
                                    .padding(vertical = 8.dp),
                                controller = colorController,
                                initialColor = selectedColor,
                                onColorChanged = { colorEnvelope ->
                                    if (colorEnvelope.fromUser) {
                                        onColorSelected(colorEnvelope.color)
                                    }
                                }
                            )

                            BrightnessSlider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .padding(vertical = 8.dp),
                                controller = colorController,
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Presets",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(bottom = 12.dp)
                            )

                            @OptIn(ExperimentalLayoutApi::class)
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                displayColors.forEach { color ->
                                    val isSelected = color == selectedColor
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(CircleShape)
                                            .background(color)
                                            .clickable { onColorSelected(color) }
                                            .border(
                                                width = if (isSelected) 3.dp else 1.dp,
                                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                                shape = CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isSelected) {
                                            Icon(
                                                Icons.Default.Check,
                                                contentDescription = null,
                                                tint = if (isLightColor(color)) Color.Black else Color.White,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isLightColor(color: Color): Boolean {
        val luminance = 0.299 * color.red + 0.587 * color.green + 0.114 * color.blue
        return luminance > 0.5
    }

    // Helper functions for safe Hex <-> Long conversion
    private fun hexToLong(hex: String): Long? {
        val cleanHex = hex.removePrefix("#").uppercase()
        return try {
            when (cleanHex.length) {
                6 -> "FF$cleanHex".toLong(16)
                8 -> cleanHex.toLong(16)
                else -> null
            }
        } catch (e: NumberFormatException) {
            null
        }
    }

    private fun longToHex(value: Long): String {
        val alpha = (value shr 24) and 0xFF
        return if (alpha == 0xFFL) {
            String.format("#%06X", value and 0xFFFFFF).uppercase()
        } else {
            String.format("#%08X", value).uppercase()
        }
    }

    override fun footer(
        text: String,
        visible: Boolean,
        sharedTransitionKey: Any?,
        textSharedTransitionKey: Any?,
        modifier: Modifier
    ) {
        if (visible) {
            items.add { _ ->
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = modifier
                        .fillMaxWidth()
                        .sharedBoundsModifier(sharedTransitionKey)
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                        .sharedElementModifier(textSharedTransitionKey),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    override fun loading(
        title: String,
        subtitle: String?,
        visible: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier
    ) {
        if (visible) {
            items.add { shape ->
                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .sharedBoundsModifier(sharedTransitionKey)
                        .clip(shape),
                    color = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                                )
                                if (subtitle != null) {
                                    Text(
                                        text = subtitle,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }

    override fun subHeader(
        text: String,
        visible: Boolean,
        sharedTransitionKey: Any?,
        textSharedTransitionKey: Any?,
        modifier: Modifier
    ) {
        if (visible) {
            items.add { _ ->
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .sharedBoundsModifier(sharedTransitionKey)
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                        .sharedElementModifier(textSharedTransitionKey)
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun searchBar(
        query: String,
        onQueryChange: (String) -> Unit,
        placeholder: String,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        modifier: Modifier
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled

                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .sharedBoundsModifier(sharedTransitionKey),
                    shape = shape,
                    color = MaterialTheme.colorScheme.surfaceContainerHigh
                ) {
                    var expanded by remember { mutableStateOf(false) }

                    SearchBarDefaults.InputField(
                        query = query,
                        onQueryChange = onQueryChange,
                        onSearch = { expanded = false },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        enabled = isEnabled,
                        placeholder = { Text(placeholder) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    override fun items(
        count: Int,
        key: ((index: Int) -> Any)?,
        contentType: (index: Int) -> Any?,
        visible: Boolean,
        itemContent: SettingsSectionScope.(Int) -> Unit
    ) {
        if (visible) {
            for (i in 0 until count) {
                itemContent(i)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun fullScreenSearch(
        query: String,
        onQueryChange: (String) -> Unit,
        onSearch: (String) -> Unit,
        expanded: Boolean,
        onExpandedChange: (Boolean) -> Unit,
        placeholder: String,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        modifier: Modifier,
        content: @Composable ColumnScope.() -> Unit
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val searchBarState = rememberSearchBarState()
                val textFieldState = rememberTextFieldState(query)
                val scope = rememberCoroutineScope()

                LaunchedEffect(expanded) {
                    if (expanded && searchBarState.currentValue == SearchBarValue.Collapsed) {
                        searchBarState.animateToExpanded()
                    } else if (!expanded && searchBarState.currentValue == SearchBarValue.Expanded) {
                        searchBarState.animateToCollapsed()
                    }
                }

                LaunchedEffect(searchBarState.currentValue) {
                    val isActuallyExpanded = searchBarState.currentValue == SearchBarValue.Expanded
                    if (isActuallyExpanded != expanded) {
                        onExpandedChange(isActuallyExpanded)
                    }
                }

                LaunchedEffect(query) {
                    if (textFieldState.text.toString() != query) {
                        textFieldState.setTextAndPlaceCursorAtEnd(query)
                    }
                }

                LaunchedEffect(textFieldState.text) {
                    val newText = textFieldState.text.toString()
                    if (newText != query) {
                        onQueryChange(newText)
                    }
                }

                val inputField = @Composable {
                    SearchBarDefaults.InputField(
                        textFieldState = textFieldState,
                        searchBarState = searchBarState,
                        onSearch = {
                            onSearch(it)
                            scope.launch { searchBarState.animateToCollapsed() }
                        },
                        enabled = isEnabled,
                        placeholder = { Text(placeholder) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .sharedBoundsModifier(sharedTransitionKey)
                ) {
                    SearchBar(
                        state = searchBarState,
                        inputField = inputField,
                        modifier = Modifier.fillMaxWidth(),
                        shape = shape
                    )
                }

                ExpandedFullScreenSearchBar(
                    state = searchBarState,
                    inputField = inputField,
                    content = content
                )
            }
        }
    }

    override fun slider(
        title: String,
        value: Float,
        onValueChange: (Float) -> Unit,
        onValueChangeFinished: (() -> Unit)?,
        valueRange: ClosedFloatingPointRange<Float>,
        steps: Int,
        showValue: Boolean,
        valueLabel: (Float) -> String,
        showMinMax: Boolean,
        enablePreciseControls: Boolean,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        subtitleSharedTransitionKey: Any?,
        modifier: Modifier
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled
                val alpha = if (isEnabled) 1f else 0.38f

                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .sharedBoundsModifier(sharedTransitionKey)
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
                                Box(
                                    modifier = Modifier
                                        .padding(end = 16.dp)
                                        .then(if (!isEnabled) Modifier.alpha(alpha) else Modifier)
                                ) {
                                    icon()
                                }
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha),
                                    modifier = Modifier.sharedElementModifier(titleSharedTransitionKey)
                                )
                                if (subtitle != null) {
                                    Text(
                                        text = subtitle,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha),
                                        modifier = Modifier.sharedElementModifier(subtitleSharedTransitionKey)
                                    )
                                }
                            }
                            if (showValue) {
                                Text(
                                    text = valueLabel(value),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = alpha),
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (enablePreciseControls) {
                                IconButton(
                                    onClick = {
                                        val step = if (steps > 0) (valueRange.endInclusive - valueRange.start) / (steps + 1) else (valueRange.endInclusive - valueRange.start) / 100f
                                        onValueChange((value - step).coerceIn(valueRange))
                                        onValueChangeFinished?.invoke()
                                    },
                                    enabled = isEnabled && value > valueRange.start,
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = "Decrease",
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Slider(
                                value = value,
                                onValueChange = onValueChange,
                                onValueChangeFinished = onValueChangeFinished,
                                valueRange = valueRange,
                                steps = steps,
                                enabled = isEnabled,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(top = if (enablePreciseControls) 0.dp else 8.dp)
                            )

                            if (enablePreciseControls) {
                                IconButton(
                                    onClick = {
                                        val step = if (steps > 0) (valueRange.endInclusive - valueRange.start) / (steps + 1) else (valueRange.endInclusive - valueRange.start) / 100f
                                        onValueChange((value + step).coerceIn(valueRange))
                                        onValueChangeFinished?.invoke()
                                    },
                                    enabled = isEnabled && value < valueRange.endInclusive,
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Increase",
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        if (showMinMax) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = if (enablePreciseControls) 40.dp else 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = valueLabel(valueRange.start),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha)
                                )
                                Text(
                                    text = valueLabel(valueRange.endInclusive),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha)
                                )
                            }
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
        onRemove: (String) -> Unit,
        enabled: Boolean,
        visible: Boolean,
        sharedTransitionKey: Any?,
        titleSharedTransitionKey: Any?,
        modifier: Modifier
    ) {
        if (visible) {
            items.add { shape ->
                val isEnabled = sectionEnabled && enabled

                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .sharedBoundsModifier(sharedTransitionKey)
                        .clip(shape),
                    color = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    KeywordEditor(
                        title = title,
                        placeholder = placeholder,
                        keywords = keywords,
                        onAdd = onAdd,
                        onRemove = onRemove,
                        enabled = isEnabled,
                        titleSharedTransitionKey = titleSharedTransitionKey,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    private fun Modifier.sharedElementModifier(key: Any?): Modifier {
        if (key == null || sharedTransitionScope == null || animatedVisibilityScope == null) return this
        return with(sharedTransitionScope) {
            this@sharedElementModifier.sharedBounds(
                rememberSharedContentState(key = key),
                animatedVisibilityScope = animatedVisibilityScope
            )
        }
    }

    @OptIn(ExperimentalSharedTransitionApi::class)
    @Composable
    private fun Modifier.sharedBoundsModifier(key: Any?): Modifier {
        if (key == null || sharedTransitionScope == null || animatedVisibilityScope == null) return this
        return with(sharedTransitionScope) {
            this@sharedBoundsModifier.sharedBounds(
                rememberSharedContentState(key = key),
                animatedVisibilityScope = animatedVisibilityScope
            )
        }
    }
}