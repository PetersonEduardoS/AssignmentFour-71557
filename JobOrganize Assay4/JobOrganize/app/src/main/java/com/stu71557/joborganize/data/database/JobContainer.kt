package com.stu71557.joborganize.data.database

import android.content.Context
import com.stu71557.joborganize.data.repository.JobRepository

class JobContainer(private val context: Context) {
    val jobRepository: JobRepository by lazy {
        JobRepository(JobDatabase.getDatabase(context).jobDetailsDao)
    }
}