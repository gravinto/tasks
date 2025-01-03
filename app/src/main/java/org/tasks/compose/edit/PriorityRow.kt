package org.tasks.compose.edit

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.tasks.R
import org.tasks.compose.TaskEditRow
import org.tasks.data.entity.Task
import org.tasks.kmp.org.tasks.themes.ColorProvider.priorityColor
import org.tasks.themes.TasksTheme

@Composable
fun PriorityRow(
    priority: Int,
    onChangePriority: (Int) -> Unit,
    desaturate: Boolean,
) {
    TaskEditRow(
        iconRes = R.drawable.ic_outline_flag_24px,
        content = {
            PriorityLabeled(
                selected = priority,
                onClick = { onChangePriority(it) },
                desaturate = desaturate,
            )
        },
    )
}

@Composable
fun LabelText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(end = 16.dp),
        color = MaterialTheme.colorScheme.onSurface,
    )
}

@Composable
fun Priority(
    selected: Int,
    onClick: (Int) -> Unit = {},
    desaturate: Boolean,
) {
    // Arrange as Eisenhower Matrix
    Row(horizontalArrangement = Arrangement.Center) {
        Column(
            modifier = Modifier.weight(0.4f),
            horizontalAlignment = Alignment.End

        ) {
            Row(modifier = Modifier.height(25.dp)) { LabelText("") }
            Row(modifier = Modifier.height(25.dp)) { LabelText("Important") }
            Row(modifier = Modifier.height(25.dp)) { LabelText("N/Important") }
        }
        Column(
            modifier = Modifier.weight(0.25f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.height(25.dp)) { LabelText("Urgent") }
            Row(modifier = Modifier.height(25.dp)) {
                PriorityButton(
                    priority = Task.Priority.HIGH,
                    selected = selected,
                    onClick = onClick,
                    desaturate = desaturate,
                )
            }
            Row(modifier = Modifier.height(25.dp)) {
                PriorityButton(
                    priority = Task.Priority.LOW,
                    selected = selected,
                    onClick = onClick,
                    desaturate = desaturate,
                )
            }
        }
        Column(
            modifier = Modifier.weight(0.3f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.height(25.dp)) { LabelText("N/Urgent") }
            Row(modifier = Modifier.height(25.dp)) {
                PriorityButton(
                    priority = Task.Priority.MEDIUM,
                    selected = selected,
                    onClick = onClick,
                    desaturate = desaturate,
                )
            }
            Row(modifier = Modifier.height(25.dp)) {
                PriorityButton(
                    priority = Task.Priority.NONE,
                    selected = selected,
                    onClick = onClick,
                    desaturate = desaturate,
                )
            }
        }
    }
}

@Composable
fun PriorityLabeled(
    selected: Int,
    onClick: (Int) -> Unit = {},
    desaturate: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                end = dimensionResource(id = R.dimen.keyline_first)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        /*Text(
            text = stringResource(id = R.string.TEA_importance_label),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(end = 16.dp),
            color = MaterialTheme.colorScheme.onSurface,
        )*/
        Spacer(modifier = Modifier.weight(1f))
        Priority(selected = selected, onClick = onClick, desaturate = desaturate)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.PriorityButton(
    @Task.Priority priority: Int,
    selected: Int,
    desaturate: Boolean,
    onClick: (Int) -> Unit,
) {
    val color = Color(
        priorityColor(
            priority = priority,
            isDarkMode = isSystemInDarkTheme(),
            desaturate = desaturate,
        )
    )
    CompositionLocalProvider(
        LocalMinimumInteractiveComponentSize provides Dp.Unspecified,
    ) {
        RadioButton(
            selected = priority == selected,
            onClick = { onClick(priority) },
            colors = RadioButtonDefaults.colors(
                selectedColor = color,
                unselectedColor = color,
            ),
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 5.dp)
        )
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PriorityPreview() {
    TasksTheme {
        PriorityRow(
            priority = Task.Priority.NONE,
            onChangePriority = {},
            desaturate = true,
        )
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PriorityPreviewNoDesaturate() {
    TasksTheme {
        PriorityRow(
            priority = Task.Priority.MEDIUM,
            onChangePriority = {},
            desaturate = false,
        )
    }
}

@ExperimentalComposeUiApi
@Preview(locale = "de", widthDp = 320, showBackground = true)
@Composable
fun PriorityNarrowWidth() {
    TasksTheme {
        PriorityRow(
            priority = Task.Priority.MEDIUM,
            onChangePriority = {},
            desaturate = false,
        )
    }
}
