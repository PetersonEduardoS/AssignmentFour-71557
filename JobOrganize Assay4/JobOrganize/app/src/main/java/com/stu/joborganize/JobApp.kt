package com.stu.joborganize

import android.app.Application
import com.stu.joborganize.data.database.JobContainer

class JobApp : Application() {

    lateinit var container: JobContainer

    override fun onCreate() {
        super.onCreate()
        container = JobContainer(this)
    }
}