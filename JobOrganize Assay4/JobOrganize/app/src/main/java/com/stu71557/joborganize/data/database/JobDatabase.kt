package com.stu71557.joborganize.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stu71557.joborganize.data.model.JobDetails

@Database(entities = [JobDetails::class], version = 1)
abstract class JobDatabase : RoomDatabase() {
    abstract val jobDetailsDao: JobDetailsDao

    companion object {
        @Volatile
        private var INSTANCE: JobDatabase? = null

        fun getDatabase(context: Context): JobDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext, JobDatabase::class.java, "job_database"
                ).build()
                    .also { INSTANCE = it }

            }
        }
    }
}