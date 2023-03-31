package anand.textme.crud3.di

import anand.textme.crud3.data.TodoDatabase
import anand.textme.crud3.data.TodoRepository
import anand.textme.crud3.data.TodoRepositoryImpl
import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(
    SingletonComponent::class
)
object AppModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(app,TodoDatabase::class.java,"todo_db").build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(db.dao)
    }
}