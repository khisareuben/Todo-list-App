package android.khisareuben.todolist.presentation.add_todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import android.khisareuben.todolist.data.local.entity.TaskEntity
import android.khisareuben.todolist.data.repository.PriorityRepository
import android.khisareuben.todolist.data.repository.TaskRepository
import android.khisareuben.todolist.model.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val priorityRepository: PriorityRepository
) : ViewModel() {
    private val _priorities = MutableStateFlow<List<Priority>>(emptyList())
    val priorities = _priorities.asStateFlow()

    private val _selectedPriority = MutableStateFlow<Priority?>(null)
    val selectedPriority = _selectedPriority.asStateFlow()

    init {
        getPriorities()
    }

    private fun getPriorities() {
        viewModelScope.launch {
            _priorities.value = withContext(Dispatchers.IO) { priorityRepository.getAllPriority() }
        }
    }

    fun selectPriority(priority: Priority) {
        _selectedPriority.value = priority
    }

    fun insertTask(title: String, desc: String) {
        viewModelScope.launch {
            val task = TaskEntity(
                title = title,
                description = desc,
                priorityId = selectedPriority.value!!.id
            )
            withContext(Dispatchers.IO) {
                taskRepository.insertTask(task)
            }
        }
    }
}