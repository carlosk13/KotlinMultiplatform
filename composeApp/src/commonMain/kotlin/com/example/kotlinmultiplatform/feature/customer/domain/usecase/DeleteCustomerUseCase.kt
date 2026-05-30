package com.example.kotlinmultiplatform.feature.customer.domain.usecase

import com.example.kotlinmultiplatform.feature.customer.domain.repository.CustomerRepository

class DeleteCustomerUseCase(private val repository: CustomerRepository) {
    suspend operator fun invoke(id: String) = repository.deleteById(id)
}
