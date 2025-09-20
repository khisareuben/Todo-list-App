package android.khisareuben.todolist.presentation.todo_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import android.khisareuben.todolist.R
import android.khisareuben.todolist.model.Task
import android.khisareuben.todolist.presentation.LocalThemeColor
import android.khisareuben.todolist.utils.TaskFilterType

@Composable
fun TodoListRoute(
    modifier: Modifier = Modifier,
    navigateToAddTask: () -> Unit = {},
    navigateToEditTask: (taskId: Int) -> Unit = {},
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val task by viewModel.tasks.collectAsStateWithLifecycle()
    TodoListScreen(
        modifier = modifier,
        navigateToAddTask = navigateToAddTask,
        categories = viewModel.categories,
        onFilterClick = { filterType ->
            viewModel.selectCategory(filterType)
        },
        selectedCategory = selectedCategory,
        tasks = task,
        onCompleteTask = { taskId, isCompleted ->
            viewModel.setTaskCompleted(taskId, isCompleted)
        },
        onDeleteTask = { task ->
            viewModel.deleteTask(task)
        },
        onEditTask = navigateToEditTask
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
    navigateToAddTask: () -> Unit = {},
    categories: List<TaskFilterType> = listOf(TaskFilterType.ALL, TaskFilterType.UNCOMPLETED),
    onFilterClick: (TaskFilterType) -> Unit = {},
    selectedCategory: TaskFilterType = TaskFilterType.ALL,
    tasks: List<Task> = emptyList(),
    onCompleteTask: (Int, Boolean) -> Unit = { _, _ -> },
    onDeleteTask: (Task) -> Unit = {},
    onEditTask: (Int) -> Unit = {}
) {
    val theme = LocalThemeColor.current
    Scaffold(
        modifier = modifier.fillMaxSize(), topBar = {
            TopAppBar(title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ToDo List",
                        fontWeight = FontWeight.Bold,
                        color = theme.buttonColor
                    )
                }

            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = theme.backgroundColor
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddTask,
                containerColor = theme.buttonColor
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }) { innerPadding ->
        Column(
            Modifier
                .background(theme.backgroundColor)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(Modifier.height(12.dp))
            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                items(categories) {
                    FilterChip(
                        selected = selectedCategory == it,
                        onClick = {
                            onFilterClick(it)
                        },
                        label = {
                            Text(it.label)
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = theme.filterChip,
                        )
                    )
                    Spacer(Modifier.width(8.dp))
                }
            }
            Spacer(Modifier.height(16.dp))
            if (tasks.isEmpty()) {
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painter = painterResource(R.drawable.il_no_task), null)
                    Spacer(Modifier.height(8.dp))
                    Text("No tasks", style = MaterialTheme.typography.titleLarge)
                }
            } else {
                LazyColumn {
                    items(tasks, key = { it.id }) { task ->
                        ToDoCard(
                            title = task.title,
                            description = task.description,
                            isCompleted = task.isCompleted,
                            priorityColor = task.priority.color,
                            onDeleteTask = { onDeleteTask(task) },
                            onCompleteTask = { isCompleted ->
                                onCompleteTask(task.id, isCompleted)
                            },
                            onEditTask = {
                                onEditTask(task.id)
                            }
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ToDoCard(
    title: String,
    description: String,
    isCompleted: Boolean,
    priorityColor: Int,
    onDeleteTask: () -> Unit,
    onCompleteTask: (Boolean) -> Unit,
    onEditTask: () -> Unit = {}
) {
    val theme = LocalThemeColor.current
    var cardExpanded by remember { mutableStateOf(false) }
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.EndToStart -> onDeleteTask()
                SwipeToDismissBoxValue.StartToEnd -> onEditTask()
                else -> {}
            }
            it != SwipeToDismissBoxValue.StartToEnd
        }
    )
    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        backgroundContent = {
            when (swipeToDismissBoxState.dismissDirection) {
                SwipeToDismissBoxValue.EndToStart -> {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove item",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red.copy(alpha = 0.2f))
                            .wrapContentSize(Alignment.CenterEnd)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }

                SwipeToDismissBoxValue.StartToEnd -> {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit item",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(theme.buttonColor.copy(alpha = 0.4f))
                            .wrapContentSize(Alignment.CenterEnd)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }

                else -> {}
            }
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = {
                cardExpanded = !cardExpanded
            },
            colors = CardDefaults.cardColors(containerColor = Color(priorityColor))
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        title,
                        style = MaterialTheme.typography.titleMedium,
                        color = theme.filterChipText
                    )
                    Spacer(Modifier.weight(1f))
                    Checkbox(checked = isCompleted, onCheckedChange = onCompleteTask)
                }
                AnimatedVisibility(cardExpanded) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = theme.filterChipText
                    )
                }
            }
        }
    }
}
