package fr.paeelluu.compose_settings_ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ColorLens
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonColors
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalSharedTransitionApi::class)
internal class SettingsSectionScopeImpl(
    private val sharedTransitionScope: SharedTransitionScope? = null,
    private val animatedVisibilityScope: AnimatedVisibilityScope? = null
) : SettingsSectionScope {
    internal val items = mutableListOf<@Composable (Shape) -> Unit>()

    override fun item(content: @Composable (Shape) -> Unit) {
        items.add(content)
    }

    override fun action(
        title: String,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        sharedTransitionKey: Any?,
        onClick: () -> Unit
    ) {
        items.add { shape ->
            SettingsItemBase(
                title = title,
                subtitle = subtitle,
                shape = shape,
                leadingContent = icon,
                onClick = onClick,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                sharedTransitionKey = sharedTransitionKey
            )
        }
    }

    override fun switch(
        title: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        sharedTransitionKey: Any?
    ) {
        items.add { shape ->
            SettingsItemBase(
                title = title,
                subtitle = subtitle,
                shape = shape,
                leadingContent = icon,
                onClick = { onCheckedChange(!checked) },
                trailingContent = { Switch(checked = checked, onCheckedChange = onCheckedChange) },
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                sharedTransitionKey = sharedTransitionKey
            )
        }
    }

    override fun checkbox(
        title: String,
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        sharedTransitionKey: Any?
    ) {
        items.add { shape ->
            SettingsItemBase(
                title = title,
                subtitle = subtitle,
                shape = shape,
                leadingContent = icon,
                onClick = { onCheckedChange(!checked) },
                trailingContent = { Checkbox(checked = checked, onCheckedChange = onCheckedChange) },
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                sharedTransitionKey = sharedTransitionKey
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
        icon: (@Composable () -> Unit)?,
        isError: Boolean,
        supportingText: String?,
        sharedTransitionKey: Any?
    ) {
        items.add { shape ->
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

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(sharedModifier)
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
                        isError = isError,
                        supportingText = supportingText?.let { { Text(it) } },
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
        sharedTransitionKey: Any?,
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
                },
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                sharedTransitionKey = sharedTransitionKey
            )
        }
    }

    @OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3ExpressiveApi::class)
    override fun <T> segmentedButton(
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String,
        sharedTransitionKey: Any?
    ) {
        items.add { shape ->
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

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(sharedModifier)
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
                            colors = ToggleButtonDefaults.toggleButtonColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                checkedContainerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onSurface,
                                checkedContentColor = MaterialTheme.colorScheme.onPrimary,
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

    override fun stepper(
        title: String,
        value: Int,
        onValueChange: (Int) -> Unit,
        valueRange: IntRange,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        sharedTransitionKey: Any?
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
                },
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                sharedTransitionKey = sharedTransitionKey
            )
        }
    }

    override fun info(text: String, icon: (@Composable () -> Unit)?, sharedTransitionKey: Any?) {
        items.add { shape ->
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

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(sharedModifier)
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
        sharedTransitionKey: Any?,
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
                },
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
                sharedTransitionKey = sharedTransitionKey
            )
        }
    }

    override fun expandableGroup(
        title: String,
        icon: (@Composable () -> Unit)?,
        sharedTransitionKey: Any?,
        content: @Composable SettingsSectionScope.() -> Unit
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
                    val innerScope = remember {
                        SettingsSectionScopeImpl(
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }
                    innerScope.items.clear()
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

    override fun <T> selector(
        title: String,
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        sharedTransitionKey: Any?
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
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Expand options",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.rotate(arrowRotation)
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

    @OptIn(ExperimentalMaterial3Api::class)
    override fun <T> dialogSelector(
        title: String,
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        sharedTransitionKey: Any?
    ) {
        items.add { shape ->
            var showDialog by remember { mutableStateOf(false) }

            SettingsItemBase(
                title = title,
                subtitle = subtitle ?: displayText(selectedOption),
                shape = shape,
                leadingContent = icon,
                onClick = { showDialog = true },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
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

    override fun <T> radioButtonGroup(
        options: List<T>,
        selectedOption: T,
        onOptionSelected: (T) -> Unit,
        displayText: (T) -> String,
        sharedTransitionKey: Any?
    ) {
        items.add { shape ->
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

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(sharedModifier)
                    .clip(shape),
                color = MaterialTheme.colorScheme.surfaceContainer
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    options.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onOptionSelected(option) }
                                .padding(horizontal = 24.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = option == selectedOption,
                                onClick = null
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = displayText(option),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
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
        sharedTransitionKey: Any?
    ) {
        items.add { shape ->
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

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(sharedModifier)
                    .clip(shape),
                color = MaterialTheme.colorScheme.surfaceContainer
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    options.forEach { option ->
                        val isChecked = selectedOptions.contains(option)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
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
                                onCheckedChange = null
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = displayText(option),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
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
        sharedTransitionKey: Any?
    ) {
        items.add { shape ->
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

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(sharedModifier)
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
                        Text(
                            text = "${valueLabel(value.start)} - ${valueLabel(value.endInclusive)}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                    RangeSlider(
                        value = value,
                        onValueChange = onValueChange,
                        onValueChangeFinished = onValueChangeFinished,
                        valueRange = valueRange,
                        steps = steps,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
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
        sharedTransitionKey: Any?
    ) {
        items.add { shape ->
            var showDialog by remember { mutableStateOf(false) }
            val timePickerState = rememberTimePickerState(
                initialHour = hour,
                initialMinute = minute,
                is24Hour = is24Hour
            )

            SettingsItemBase(
                title = title,
                subtitle = subtitle ?: String.format(Locale.getDefault(), "%02d:%02d", hour, minute),
                shape = shape,
                leadingContent = icon,
                onClick = { showDialog = true },
                trailingContent = {
                    Icon(Icons.Default.TimeToLeave, contentDescription = null)
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

    @OptIn(ExperimentalMaterial3Api::class)
    override fun datePicker(
        title: String,
        selectedDateMillis: Long?,
        onDateSelected: (Long?) -> Unit,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        sharedTransitionKey: Any?
    ) {
        items.add { shape ->
            var showDialog by remember { mutableStateOf(false) }
            val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDateMillis)
            val formatter = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }

            SettingsItemBase(
                title = title,
                subtitle = subtitle ?: selectedDateMillis?.let { formatter.format(Date(it)) } ?: "Select date",
                shape = shape,
                leadingContent = icon,
                onClick = { showDialog = true },
                trailingContent = {
                    Icon(Icons.Default.DateRange, contentDescription = null)
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

    @OptIn(ExperimentalMaterial3Api::class)
    override fun colorPicker(
        title: String,
        selectedColor: Color,
        onColorSelected: (Color) -> Unit,
        colors: List<Color>,
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        sharedTransitionKey: Any?
    ) {
        items.add { shape ->
            var showSheet by remember { mutableStateOf(false) }
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
                title = title,
                subtitle = subtitle,
                shape = shape,
                leadingContent = icon,
                onClick = { showSheet = true },
                trailingContent = {
                    Surface(
                        modifier = Modifier.size(28.dp),
                        shape = CircleShape,
                        color = selectedColor,
                        border = androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {}
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

    override fun footer(text: String, sharedTransitionKey: Any?) {
        items.add { _ ->
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

            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .then(sharedModifier)
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }

    override fun loading(title: String, subtitle: String?, sharedTransitionKey: Any?) {
        items.add { shape ->
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

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(sharedModifier)
                    .clip(shape),
                color = MaterialTheme.colorScheme.surfaceContainer
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(title, style = MaterialTheme.typography.titleMedium)
                            if (subtitle != null) {
                                Text(subtitle, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }

    override fun subHeader(text: String, sharedTransitionKey: Any?) {
        items.add { _ ->
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

            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .then(sharedModifier)
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun searchBar(query: String, onQueryChange: (String) -> Unit, placeholder: String, sharedTransitionKey: Any?) {
        items.add { shape ->
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(sharedModifier)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(shape)
            ) {
                SearchBar(
                    query = query,
                    onQueryChange = onQueryChange,
                    onSearch = {},
                    active = false,
                    onActiveChange = {},
                    placeholder = { Text(placeholder) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = SearchBarDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    )
                ) {}
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
        subtitle: String?,
        icon: (@Composable () -> Unit)?,
        sharedTransitionKey: Any?
    ) {
        items.add { shape ->
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

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(sharedModifier)
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
                        onValueChangeFinished = onValueChangeFinished,
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
        onRemove: (String) -> Unit,
        sharedTransitionKey: Any?
    ) {
        items.add { shape ->
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

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(sharedModifier)
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
