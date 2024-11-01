package com.stu.joborganize.ui.screens.contracted_decorators

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.stu.joborganize.data.model.Decorator
import com.stu.joborganize.ui.screens.decorators.DecoratorsViewModel
import com.stu.joborganize.ui.screens.home.TextWithLabel

@Composable
fun ContractedDecoratorsScreen(
    modifier: Modifier = Modifier,
    viewModel: DecoratorsViewModel = viewModel(factory = DecoratorsViewModel.Factory)
) {

    LaunchedEffect(Unit) {
        viewModel.getReservedDecorators()
    }

    val decorators by viewModel.reservedDecorators.collectAsState()

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            Text(text = "Contracted Decorators", style = MaterialTheme.typography.titleLarge)
        }

        items(decorators, key = { it.id }) { decorator ->
            DecoratorItem(
                decorator = decorator,
                onClickDelete = { viewModel.deleteReservedDecorator(decorator) }
            )
        }
    }

}


@Composable
private fun DecoratorItem(
    decorator: Decorator,
    onClickDelete: () -> Unit,
    modifier: Modifier = Modifier
) {

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
                horizontalAlignment = Alignment.End
            ) {
                IconButton(onClick = { onClickDelete() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = null
                    )
                }

            }
        }

    }


}
