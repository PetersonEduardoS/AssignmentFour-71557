package com.stu71557.joborganize

import android.app.Application
import com.stu71557.joborganize.data.database.JobContainer

class JobApp : Application() {

    lateinit var container: JobContainer

    override fun onCreate() {
        super.onCreate()
        container = JobContainer(this)
    }
}