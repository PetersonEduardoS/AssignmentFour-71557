package com.stu.joborganize.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stu.joborganize.data.model.Decorator
import com.stu.joborganize.data.model.JobDetails

@Database(entities = [JobDetails::class, Decorator::class], version = 2)
abstract class JobDatabase : RoomDatabase() {
    abstract val jobDetailsDao: JobDetailsDao
    abstract val reservedDecoratorsDao: ReservedDecoratorsDao

    companion object {
        @Volatile
        private var INSTANCE: JobDatabase? = null

        fun getDatabase(context: Context): JobDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext, JobDatabase::class.java, "job_database"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }

            }
        }
    }
}