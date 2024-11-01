package com.stu.joborganize.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stu.joborganize.data.model.JobDetails
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(
    showDialogAddNewJob: Boolean,
    onDialogAddNewJobDismiss: () -> Unit,
    onNavigateToDecorators: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {

    val jobs by viewModel.getAllJobs().collectAsState(initial = emptyList())

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)

    ) {
        Text(text = "All Jobs", style = MaterialTheme.typography.titleLarge)

        if (jobs.isEmpty()) {
            Text(text = "No jobs found", style = MaterialTheme.typography.titleMedium)
        }

        Jobs(
            items = jobs,
            onClickDelete = viewModel::deleteJob,
            onNavigateToDecorators = onNavigateToDecorators
        )
    }
    if (showDialogAddNewJob) {
        DialogAddNewJob(onDismiss = onDialogAddNewJobDismiss, onAddNewJob = {
            viewModel.insertJob(it)
        })
    }

}

@Composable
private fun Jobs(
    items: List<JobDetails>,
    onClickDelete: (JobDetails) -> Unit,
    onNavigateToDecorators: (Int) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        items(items) { job ->
            JobItem(
                job = job,
                onClickDelete = { onClickDelete(job) },
                onNavigateToDecorators = onNavigateToDecorators
            )
        }
    }
}

@Composable
private fun JobItem(
    job: JobDetails,
    onClickDelete: () -> Unit,
    onNavigateToDecorators: (Int) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        )
    ) {

        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
        ) {

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                TextWithLabel(label = "Location", text = job.location)
                TextWithLabel(label = "Type", text = job.jobType)
                TextWithLabel(label = "Start Date", text = job.startDate)
                TextWithLabel(label = "End Date", text = job.endDate)
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.End
            ) {
                IconButton(onClick = { onClickDelete() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                }

                TextButton(onClick = { onNavigateToDecorators(job.id) }) {
                    Text(
                        text = "Find a decorator for this service",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

            }
        }
    }
}

@Composable
fun TextWithLabel(label: String, text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Text(
            text = "$label: ",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
        SelectionContainer {
            Text(text = text, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun DialogAddNewJob(
    onDismiss: () -> Unit, onAddNewJob: (JobDetails) -> Unit, modifier: Modifier = Modifier
) {

    var location by remember { mutableStateOf("") }
    var locationIsError by remember { mutableStateOf(false) }
    var type by remember { mutableStateOf("Painting") }
    var startDate by remember { mutableStateOf("") }
    var startDateIsError by remember { mutableStateOf(false) }
    var endDate by remember { mutableStateOf("") }
    var endDateIsError by remember { mutableStateOf(false) }
    var showModel by remember { mutableStateOf(false) }

    Dialog(onDismiss) {
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surfaceContainerLow, Shapes().large),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(5.dp))

            Text(text = "Add New Job", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = location,
                onValueChange = {
                    location = it
                    locationIsError = false
                },
                label = { Text(text = "Location") },
                singleLine = true,
                isError = locationIsError,
                shape = Shapes().large
            )

            JobTypes(
                modifier = Modifier
                    .width(300.dp),
                onSelected = { type = it })

            Box {
                OutlinedTextField(
                    value = startDate,
                    onValueChange = { startDate = it },
                    label = { Text(text = "Start Date") },
                    isError = startDateIsError,
                    readOnly = true,
                    shape = Shapes().large
                )
                Box(modifier = Modifier
                    .padding(top = 10.dp)
                    .clip(Shapes().large)
                    .clickable { showModel = true }
                    .size(width = 280.dp, height = 55.dp))
            }


            Box {
                OutlinedTextField(
                    value = endDate,
                    onValueChange = { endDate = it },
                    label = { Text(text = "End Date") },
                    isError = endDateIsError,
                    readOnly = true,
                    shape = Shapes().large
                )
                Box(modifier = Modifier
                    .padding(top = 10.dp)
                    .clip(Shapes().large)
                    .clickable { showModel = true }
                    .size(width = 280.dp, height = 55.dp))
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                TextButton(
                    onClick = onDismiss,
                ) {
                    Text(text = "Cancel", style = MaterialTheme.typography.titleMedium)
                }
                TextButton(
                    onClick = {
                        when {
                            location.isEmpty() || location.isBlank() -> locationIsError = true
                            startDate.isEmpty() || startDate.isBlank() -> startDateIsError = true
                            endDate.isEmpty() || endDate.isBlank() -> endDateIsError = true
                            else -> {
                                onAddNewJob(
                                    JobDetails(
                                        location = location,
                                        jobType = type,
                                        startDate = startDate,
                                        endDate = endDate
                                    )
                                )
                                onDismiss()
                            }
                        }
                    },
                ) {
                    Text(text = "Save", style = MaterialTheme.typography.titleMedium)
                }
            }
            Spacer(modifier = Modifier.height(5.dp))

        }
    }

    if (showModel) {
        DateRangePickerModal(onDateRangeSelected = {
            val start = Date(it.first!!)
            val end = Date(it.second!!)
            startDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(start)
            endDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(end)

            startDateIsError = false
            endDateIsError = false
        }, onDismiss = { showModel = false })
    }

}

@Composable
fun JobTypes(
    onSelected: (String) -> Unit, modifier: Modifier = Modifier
) {
    val types = listOf("Painting", "Wallpapering", "Both")
    var selectedIndex by remember { mutableIntStateOf(0) }

    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        types.fastForEachIndexed { index, type ->

            SegmentedButton(
                modifier = Modifier
                    .height(40.dp),
                shape = SegmentedButtonDefaults.itemShape(index, types.size),
                onClick = {
                    selectedIndex = index
                    onSelected(type)
                },
                selected = index == selectedIndex,
            ) {
                Text(
                    text = type,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateRangePickerModal(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {

    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(modifier = modifier, onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = {
            onDateRangeSelected(
                Pair(
                    dateRangePickerState.selectedStartDateMillis,
                    dateRangePickerState.selectedEndDateMillis
                )
            )
            onDismiss()
        }) {
            Text(text = "OK")
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(text = "Cancel")
        }
    }) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(text = "Select Date Range")
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp),
        )
    }
}