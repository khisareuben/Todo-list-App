package android.khisareuben.todolist.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import android.khisareuben.todolist.data.local.entity.PriorityEntity
import android.khisareuben.todolist.data.local.entity.TaskEntity
import android.khisareuben.todolist.model.Task

data class TaskAndPriority(
    @Embedded val task: TaskEntity,
    @Relation(
        parentColumn = "priorityId",
        entityColumn = "id"
    )
    val priority: PriorityEntity
) {
    fun toTask() = Task(
        id = task.id,
        title = task.title,
        description = task.description,
        isCompleted = task.isCompleted,
        priorityId = task.priorityId,
        createdAt = task.createdAt,
        priority = priority.toPriority()
    )
}