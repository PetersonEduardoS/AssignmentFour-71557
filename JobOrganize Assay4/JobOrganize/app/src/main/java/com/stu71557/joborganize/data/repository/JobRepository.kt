package com.stu71557.joborganize.data.repository

import com.stu71557.joborganize.data.database.JobDetailsDao
import com.stu71557.joborganize.data.model.JobDetails

class JobRepository(private val jobDetailsDao: JobDetailsDao) {

    fun getAllJobs() = jobDetailsDao.getAll()

    suspend fun insertJob(job: JobDetails) = jobDetailsDao.insetJob(job)

    suspend fun deleteJob(job: JobDetails) = jobDetailsDao.deleteJob(job)

}