package com.stu.joborganize.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.stu.joborganize.JobApp
import com.stu.joborganize.data.model.JobDetails
import com.stu.joborganize.data.repository.JobRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val jobRepository: JobRepository) : ViewModel() {

    fun getAllJobs() = jobRepository.getAllJobs()

    fun insertJob(job: JobDetails) = viewModelScope.launch {
        jobRepository.insertJob(job)
    }

    fun deleteJob(job: JobDetails) = viewModelScope.launch {
        jobRepository.deleteJob(job)
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as JobApp)
                HomeViewModel(application.container.jobRepository)
            }
        }
    }
}