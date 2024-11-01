package com.stu.joborganize.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job_details")
data class JobDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val location: String,
    val jobType: String,
    val startDate: String,
    val endDate: String,
)
