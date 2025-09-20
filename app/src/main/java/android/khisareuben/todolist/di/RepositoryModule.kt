package android.khisareuben.todolist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.khisareuben.todolist.data.local.dao.PriorityDao
import android.khisareuben.todolist.data.local.dao.TaskDao
import android.khisareuben.todolist.data.repository.PriorityRepository
import android.khisareuben.todolist.data.repository.PriorityRepositoryImpl
import android.khisareuben.todolist.data.repository.TaskRepository
import android.khisareuben.todolist.data.repository.TaskRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideTaskRepository(dao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun providePriorityRepository(dao: PriorityDao): PriorityRepository {
        return PriorityRepositoryImpl(dao)
    }

}