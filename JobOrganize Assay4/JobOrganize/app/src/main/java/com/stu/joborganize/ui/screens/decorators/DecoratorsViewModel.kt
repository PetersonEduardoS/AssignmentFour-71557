package com.stu.joborganize.ui.screens.decorators

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.stu.joborganize.JobApp
import com.stu.joborganize.data.model.Decorator
import com.stu.joborganize.data.model.JobDetails
import com.stu.joborganize.data.repository.JobRepository
import com.stu.joborganize.data.repository.ReservedDecoratorsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class DecoratorsViewModel(
    private val jobRepository: JobRepository,
    private val reservedDecoratorsRepository: ReservedDecoratorsRepository
) : ViewModel() {


    val job = MutableStateFlow<JobDetails?>(null)
    private val _decorators = listOf(
        Decorator(
            id = 1,
            name = "John O'Sullivan",
            location = "Dublin",
            isAvailable = true,
            jobType = "Painting",
            availableFrom = "2024-11-01",
            availableTo = "2024-11-15",
            contactNumber = "+353 87 123 4567"
        ),
        Decorator(
            id = 2,
            name = "Emma Murphy",
            location = "Galway",
            isAvailable = true,
            jobType = "Wallpapering",
            availableFrom = "2024-11-05",
            availableTo = "2024-11-20",
            contactNumber = "+353 86 234 5678"
        ),
        Decorator(
            id = 3,
            name = "Liam Byrne",
            location = "Cork",
            isAvailable = false,
            jobType = "Both",
            availableFrom = "2024-10-25",
            availableTo = "2024-11-10",
            contactNumber = "+353 85 345 6789"
        ),
        Decorator(
            id = 4,
            name = "Aoife Kelly",
            location = "Limerick",
            isAvailable = true,
            jobType = "Painting",
            availableFrom = "2024-12-01",
            availableTo = "2024-12-15",
            contactNumber = "+353 87 456 7890"
        ),
        Decorator(
            id = 5,
            name = "Sean O'Connor",
            location = "Waterford",
            isAvailable = true,
            jobType = "Both",
            availableFrom = "2024-11-10",
            availableTo = "2024-11-30",
            contactNumber = "+353 86 567 8901"
        ),
        Decorator(
            id = 6,
            name = "Sinead Doyle",
            location = "Dublin",
            isAvailable = false,
            jobType = "Wallpapering",
            availableFrom = "2024-11-15",
            availableTo = "2024-12-05",
            contactNumber = "+353 85 678 9012"
        ),
        Decorator(
            id = 7,
            name = "Conor Fitzgerald",
            location = "Kilkenny",
            isAvailable = true,
            jobType = "Painting",
            availableFrom = "2024-11-20",
            availableTo = "2024-12-10",
            contactNumber = "+353 87 789 0123"
        )
    )
    val decorators = MutableStateFlow(_decorators)
    val sortedDecorators = MutableStateFlow(_decorators)
    val reservedDecorators = MutableStateFlow(listOf<Decorator>())

    fun getJob(id: Int) = viewModelScope.launch {
        jobRepository.getAllJobs().collectLatest { jobs ->
            jobs.find { it.id == id }?.let { mJob ->
                job.value = mJob
                sortDecorators()
            }
        }
    }

    fun bookDecorator(decorator: Decorator) = viewModelScope.launch {
        reservedDecoratorsRepository.insertDecorator(decorator)
    }

    fun getReservedDecorators() = viewModelScope.launch {
        reservedDecoratorsRepository.getAllDecorators().collectLatest {
            reservedDecorators.value = it
        }
    }

    fun deleteReservedDecorator(decorator: Decorator) = viewModelScope.launch {
        reservedDecoratorsRepository.deleteDecorator(decorator.id)
    }

    private fun sortDecorators() {
        val currentJob = job.value ?: return

        val jobStartMillis =
            convertDateToMillis(currentJob.startDate, "MMM dd, yyyy") //ex: Oct 07, 2024
        val jobEndMillis = convertDateToMillis(currentJob.endDate, "MMM dd, yyyy")
        val filteredAndSortedDecorators = _decorators.filter { decorator ->
            decorator.jobType == currentJob.jobType &&
                    decorator.location == currentJob.location &&
                    //decorator.isAvailable &&
                    convertDateToMillis(decorator.availableFrom) <= jobEndMillis &&
                    convertDateToMillis(decorator.availableTo) >= jobStartMillis
        }.sortedWith(
            compareByDescending<Decorator> { it.isAvailable }
                .thenBy { it.jobType }
                .thenBy { it.location }
        )

        sortedDecorators.value = filteredAndSortedDecorators
    }

    private fun convertDateToMillis(dateString: String, format: String = "yyyy-MM-dd"): Long {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        val date = dateFormat.parse(dateString)
        return date?.time ?: 0L
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as JobApp)
                val container = application.container
                DecoratorsViewModel(container.jobRepository, container.reservedDecoratorsRepository)
            }
        }
    }
}