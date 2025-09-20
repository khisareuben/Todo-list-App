package android.khisareuben.todolist.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.khisareuben.todolist.model.Priority
import android.khisareuben.todolist.presentation.LocalThemeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoContent(
    modifier: Modifier = Modifier,
    title: String = "",
    setTitle: (String) -> Unit = {},
    desc: String = "",
    setDesc: (String) -> Unit = {},
    navigateBack: () -> Unit = {},
    onButtonClick: () -> Unit = {},
    type: ToDoScreenType = ToDoScreenType.ADD,
    selectedPriority: Priority? = null,
    priorities: List<Priority> = emptyList(),
    onSelectPriority: (Priority) -> Unit = {},
) {
    val theme = LocalThemeColor.current
    val titleText = if (type == ToDoScreenType.ADD) "Add Todo" else "Edit Todo"
    val buttonText = if (type == ToDoScreenType.ADD) "Add Task" else "Edit Task"
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(title = {
            Text(
                text = titleText,
                fontWeight = FontWeight.Bold,
                color = theme.buttonColor
            )
        }, navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
            }
        },
            actions = {
                TextButton(
                    onClick = onButtonClick, modifier = Modifier,
                    enabled = title.isNotEmpty() && desc.isNotEmpty() && selectedPriority != null
                ) {
                    Text(
                        text = buttonText,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = theme.backgroundColor
            )
        )
    }) { innerPadding ->
        Column(
            Modifier
                .background(theme.backgroundColor)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = setTitle,
                label = {
                    Text("Title")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(5.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(55.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(4.dp)
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "${desc.length} characters"
                )

                TodoTypeCard(priorities = priorities, selectedPriority = selectedPriority, onSelect = onSelectPriority)

            }
            Spacer(Modifier.height(5.dp))
            OutlinedTextField(
                value = desc, onValueChange = setDesc,
                label = {
                    Text("Description")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .height(400.dp)
                    .padding(horizontal = 16.dp),
                minLines = 4
            )
            Spacer(Modifier.height(16.dp))
            /*Button(
                onClick = onButtonClick, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                enabled = title.isNotEmpty() && desc.isNotEmpty() && selectedPriority != null
            ) {
                Text(buttonText)
            }*/
        }
    }
}

@Composable
fun TodoTypeCard(
    modifier: Modifier = Modifier,
    priorities: List<Priority>,
    selectedPriority: Priority? = null,
    onSelect: (Priority) -> Unit = {},
) {
    var priorityExpanded by remember { mutableStateOf(false) }
    val theme = LocalThemeColor.current
    Card(
        onClick = {
            priorityExpanded = true
        }, modifier = modifier.padding(start = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selectedPriority?.color != null)
                Color(selectedPriority.color)
            else Color(0xFF53C1EC)
        )
    ) {
        Box(Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
            Text(
                text = selectedPriority?.name ?: "Select priority",
                style = MaterialTheme.typography.bodyLarge,
                color = if (selectedPriority?.name != null) theme.filterChipText
                else theme.textColor
            )
            DropdownMenu(
                expanded = priorityExpanded,
                onDismissRequest = { priorityExpanded = false }
            ) {
                priorities.forEach {
                    DropdownMenuItem(
                        text = { Text(it.name) },
                        onClick = {
                            priorityExpanded = false
                            onSelect(it)
                        },
                    )
                }
            }
        }
    }
}

enum class ToDoScreenType {
    ADD, EDIT
}
