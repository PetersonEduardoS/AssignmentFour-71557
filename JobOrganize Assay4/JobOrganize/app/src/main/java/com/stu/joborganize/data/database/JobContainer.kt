package com.stu.joborganize.data.database

import android.content.Context
import com.stu.joborganize.data.repository.JobRepository
import com.stu.joborganize.data.repository.ReservedDecoratorsRepository

class JobContainer(private val context: Context) {
    val jobRepository: JobRepository by lazy {
        JobRepository(JobDatabase.getDatabase(context).jobDetailsDao)
    }

    val reservedDecoratorsRepository: ReservedDecoratorsRepository by lazy {
        ReservedDecoratorsRepository(JobDatabase.getDatabase(context).reservedDecoratorsDao)
    }
}