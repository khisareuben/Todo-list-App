package android.khisareuben.todolist.data.repository

import android.khisareuben.todolist.data.local.dao.PriorityDao
import android.khisareuben.todolist.model.Priority

interface PriorityRepository {
    suspend fun getAllPriority(): List<Priority>
}

class PriorityRepositoryImpl(private val dao: PriorityDao) : PriorityRepository {
    override suspend fun getAllPriority(): List<Priority> {
        return dao.getAllPriorities().map { it.toPriority() }
    }
}