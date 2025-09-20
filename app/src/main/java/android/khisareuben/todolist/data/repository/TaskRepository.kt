package android.khisareuben.todolist.data.repository

import android.khisareuben.todolist.data.local.dao.TaskDao
import android.khisareuben.todolist.data.local.entity.TaskEntity
import android.khisareuben.todolist.model.Task
import android.khisareuben.todolist.utils.FilterUtils
import android.khisareuben.todolist.utils.TaskFilterType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface TaskRepository {
    fun getAllTasks(filterType: TaskFilterType): Flow<List<Task>>
    suspend fun insertTask(task: TaskEntity)
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
    suspend fun getTaskById(id: Int): Task?
    suspend fun setTaskCompleted(taskId: Int, isCompleted: Boolean)
}

class TaskRepositoryImpl(private val dao: TaskDao) : TaskRepository {
    override fun getAllTasks(filterType: TaskFilterType): Flow<List<Task>> {
        val query = FilterUtils.getFilterType(filterType)
        val taskAndPriorityList = dao.getAllTasks(query)
        return taskAndPriorityList.map { it.map { it.toTask() } }
    }

    override suspend fun insertTask(task: TaskEntity) {
       return dao.insert(task)
    }

    override suspend fun updateTask(task: TaskEntity) {
        return dao.update(task)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        return dao.delete(task)
    }

    override suspend fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)?.toTask()
    }

    override suspend fun setTaskCompleted(taskId: Int, isCompleted: Boolean) {
        return dao.setTaskCompleted(taskId, isCompleted)
    }
}