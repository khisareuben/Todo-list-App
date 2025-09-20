package android.khisareuben.todolist

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import android.khisareuben.todolist.presentation.LocalThemeColor
import android.khisareuben.todolist.presentation.darkThemColors
import android.khisareuben.todolist.presentation.lightThemeColors
import android.khisareuben.todolist.theme.ToDoListTheme

@HiltAndroidApp
class App : Application()

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListTheme {
                val theme = if (isSystemInDarkTheme()) darkThemColors else lightThemeColors
                CompositionLocalProvider(LocalThemeColor provides theme) {
                    ToDoApp()
                }


            }
        }
    }
}