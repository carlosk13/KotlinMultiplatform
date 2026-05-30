package com.example.kotlinmultiplatform.feature.customer.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.kotlinmultiplatform.feature.customer.domain.model.Customer

@Composable
fun CustomerList(
    customers: List<Customer>,
    onEdit: (Customer) -> Unit,
    onDelete: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(customers, key = { it.id }) { customer ->
            CustomerItem(customer = customer, onEdit = onEdit, onDelete = onDelete)
        }
    }
}
