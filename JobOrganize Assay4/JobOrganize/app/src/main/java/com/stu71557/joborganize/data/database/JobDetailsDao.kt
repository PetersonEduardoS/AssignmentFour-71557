package com.stu71557.joborganize.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stu71557.joborganize.data.model.JobDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetJob(job: JobDetails)

    @Query("SELECT * FROM job_details")
    fun getAll(): Flow<List<JobDetails>>

    @Delete
    suspend fun deleteJob(job: JobDetails)
}