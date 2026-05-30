package com.example.kotlinmultiplatform.feature.customer.domain.usecase

import com.example.kotlinmultiplatform.feature.customer.domain.model.Customer
import com.example.kotlinmultiplatform.feature.customer.domain.repository.CustomerRepository

class SaveCustomerUseCase(private val repository: CustomerRepository) {
    suspend operator fun invoke(customer: Customer) = repository.save(customer)
}
