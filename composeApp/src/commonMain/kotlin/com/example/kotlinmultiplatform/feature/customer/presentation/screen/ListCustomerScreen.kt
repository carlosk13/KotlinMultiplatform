package com.example.kotlinmultiplatform.feature.customer.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kotlinmultiplatform.feature.customer.domain.model.Customer
import com.example.kotlinmultiplatform.feature.customer.presentation.component.CustomerList
import com.example.kotlinmultiplatform.feature.customer.presentation.component.EmptyCustomerView
import com.example.kotlinmultiplatform.feature.customer.presentation.event.CustomerEvent
import com.example.kotlinmultiplatform.feature.customer.presentation.viewmodel.CustomerViewModel

@Composable
fun ListCustomerScreen(
    viewModel: CustomerViewModel,
    onNavigateToCreate: () -> Unit,
    onNavigateToEdit: (Customer) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                state.isLoading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                state.customers.isEmpty() -> EmptyCustomerView()

                else -> CustomerList(
                    customers = state.customers,
                    onEdit = onNavigateToEdit,
                    onDelete = { id -> viewModel.onEvent(CustomerEvent.Delete(id)) }
                )
            }
        }
    }
}
