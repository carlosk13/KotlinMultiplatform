package com.example.kotlinmultiplatform.feature.customer.presentation.state

import com.example.kotlinmultiplatform.feature.customer.domain.model.Customer

data class CustomerState(
    val customers: List<Customer> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

