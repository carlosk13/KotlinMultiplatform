package com.example.kotlinmultiplatform.feature.customer.domain.usecase

import com.example.kotlinmultiplatform.feature.customer.domain.model.Customer
import com.example.kotlinmultiplatform.feature.customer.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow

class ObserveCustomersUseCase(private val repository: CustomerRepository) {
    operator fun invoke(): Flow<List<Customer>> = repository.observeAll()
}
