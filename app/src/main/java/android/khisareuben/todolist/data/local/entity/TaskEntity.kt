package android.khisareuben.todolist.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val priorityId: Int,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)