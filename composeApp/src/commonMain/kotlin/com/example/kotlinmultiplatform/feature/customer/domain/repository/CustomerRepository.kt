package com.example.kotlinmultiplatform.feature.customer.domain.repository

import com.example.kotlinmultiplatform.feature.customer.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun observeAll(): Flow<List<Customer>>
    suspend fun findById(id: String): Customer?
    suspend fun save(customer: Customer)
    suspend fun update(customer: Customer)
    suspend fun deleteById(id: String)
}
