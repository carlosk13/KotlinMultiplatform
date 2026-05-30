package com.example.kotlinmultiplatform.feature.customer.presentation.event

import com.example.kotlinmultiplatform.feature.customer.domain.model.Customer

sealed class CustomerEvent {
    data class Save(val customer: Customer) : CustomerEvent()
    data class Update(val customer: Customer) : CustomerEvent()
    data class Delete(val id: String) : CustomerEvent()
}

