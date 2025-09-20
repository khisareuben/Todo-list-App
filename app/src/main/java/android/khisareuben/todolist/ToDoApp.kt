package android.khisareuben.todolist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.khisareuben.todolist.navigation.Screen
import android.khisareuben.todolist.presentation.add_todo.AddTodoRoute
import android.khisareuben.todolist.presentation.edit_todo.EditToDoRoute
import android.khisareuben.todolist.presentation.todo_list.TodoListRoute

@Composable
fun ToDoApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.ToDoList) {
        composable<Screen.ToDoList> {
            TodoListRoute(
                modifier = modifier,
                navigateToAddTask = {
                    navController.navigate(Screen.AddTodo)
                }, navigateToEditTask = { taskId ->
                    navController.navigate(Screen.EditTodo(taskId = taskId))
                })
        }
        composable<Screen.AddTodo> {
            AddTodoRoute(modifier = modifier, navigateBack = {
                navController.navigateUp()
            })
        }
        composable<Screen.EditTodo> {
            EditToDoRoute(modifier = modifier, navigateBack = {
                navController.navigateUp()
            })
        }
    }
}