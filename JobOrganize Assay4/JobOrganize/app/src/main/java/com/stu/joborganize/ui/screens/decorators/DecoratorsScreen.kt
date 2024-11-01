package com.stu.joborganize.ui.screens.decorators

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stu.joborganize.data.model.Decorator
import com.stu.joborganize.data.model.JobDetails
import com.stu.joborganize.ui.screens.home.TextWithLabel

@Composable
fun DecoratorsScreen(
    jobId: Int?,
    modifier: Modifier = Modifier,
    viewModel: DecoratorsViewModel = viewModel(factory = DecoratorsViewModel.Factory)
) {

    val job by viewModel.job.collectAsState()
    val sortedDecorators by viewModel.sortedDecorators.collectAsState()
    val decorators by viewModel.decorators.collectAsState()

    LaunchedEffect(jobId) {
        if (jobId != null) {
            viewModel.getJob(jobId)
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {


        if (job != null) {
            JobItem(job = job!!)
        }

        HorizontalDivider()
        Decorators(
            sortedDecorators = sortedDecorators,
            decorators = decorators,
            onClickBookDecorator = viewModel::bookDecorator
        )
    }
}

@Composable
fun Decorators(
    sortedDecorators: List<Decorator>,
    decorators: List<Decorator>,
    onClickBookDecorator: (Decorator) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    var showAllDecorators by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .padding(start = 10.dp),
            ) {

                Text(

                    text = "Decorators",
                    style = MaterialTheme.typography.titleLarge
                )
                TextWithLabel("Tip", "You can select the text")

                if (sortedDecorators.isEmpty()) {
                    if (!showAllDecorators) {
                        TextWithLabel(
                            "Tip",
                            "There are no decorators available, this app uses fictitious data, click on \"Show all decorators\" to view the available decorators and make your test easier."
                        )
                        //Text(text = "", style = MaterialTheme.typography.bodyMedium)
                    }

                    TextButton(
                        onClick = { showAllDecorators = !showAllDecorators }
                    ) {
                        val text =
                            if (showAllDecorators) "Hide all decorators" else "Show all decorators"
                        Text(text = text)
                    }
                }
            }
        }

        items(sortedDecorators, key = { it.id }) { decorator ->
            DecoratorItem(decorator = decorator, onClickBookDecorator = {
                onClickBookDecorator(decorator)
                Toast.makeText(context, "Decorator booked", Toast.LENGTH_SHORT).show()
            })
        }

        if (showAllDecorators) {
            items(decorators, key = { it.id }) { decorator ->
                DecoratorItem(decorator = decorator, onClickBookDecorator = {
                    onClickBookDecorator(decorator)
                    Toast.makeText(context, "Decorator booked", Toast.LENGTH_SHORT).show()
                })
            }
        }
    }
}


@Composable
private fun DecoratorItem(
    decorator: Decorator,
    onClickBookDecorator: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
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
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                TextWithLabel(label = "Name", text = decorator.name)
                TextWithLabel(label = "Contact Number", text = decorator.contactNumber)
                TextWithLabel(label = "Location", text = decorator.location)
                TextWithLabel(
                    label = "Available",
                    text = if (decorator.isAvailable) "Yes" else "No"
                )
                TextWithLabel(label = "Job Type", text = decorator.jobType)
                TextWithLabel(label = "Available From", text = decorator.availableFrom)
                TextWithLabel(label = "Available To", text = decorator.availableTo)
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                TextButton(
                    onClick = { showDialog = true },
                    enabled = decorator.isAvailable
                ) {
                    Text(
                        text = "Book decorator",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

            }
        }

    }

    if (showDialog) {
        DialogDecorator(
            decorator = decorator,
            onDismiss = { showDialog = false },
            onClickBookDecorator = {
                onClickBookDecorator()
                showDialog = false
            }
        )
    }
}

@Composable
private fun JobItem(
    job: JobDetails
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        )
    ) {

        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text("Job Details", style = MaterialTheme.typography.titleMedium)
            TextWithLabel(label = "Location", text = job.location)
            TextWithLabel(label = "Type", text = job.jobType)
            TextWithLabel(label = "Start Date", text = job.startDate)
            TextWithLabel(label = "End Date", text = job.endDate)
        }

    }
}

@Composable
fun DialogDecorator(
    decorator: Decorator,
    onDismiss: () -> Unit,
    onClickBookDecorator: () -> Unit,
    modifier: Modifier = Modifier
) {

    Dialog(
        onDismissRequest = onDismiss,

        ) {
        Column(
            modifier = modifier
                .background(
                    MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(10.dp)
                .fillMaxWidth(),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                TextWithLabel(label = "Name", text = decorator.name)
                TextWithLabel(label = "Contact Number", text = decorator.contactNumber)
                TextWithLabel(label = "Location", text = decorator.location)
                TextWithLabel(
                    label = "Available",
                    text = if (decorator.isAvailable) "Yes" else "No"
                )
                TextWithLabel(label = "Job Type", text = decorator.jobType)
                TextWithLabel(label = "Available From", text = decorator.availableFrom)
                TextWithLabel(label = "Available To", text = decorator.availableTo)
            }


            Box(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            ) {

                OutlinedButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd),
                    onClick = onClickBookDecorator,
                    enabled = decorator.isAvailable
                ) {
                    Text(
                        text = "Book decorator",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }


        }
    }
}