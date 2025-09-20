package android.khisareuben.todolist.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.khisareuben.todolist.model.Priority

@Entity("priority")
data class PriorityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val color: Int
) {
    fun toPriority() = Priority(
        id = id,
        name = name,
        color = color
    )
}