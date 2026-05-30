package com.example.kotlinmultiplatform.feature.customer.data.repository

import com.example.kotlinmultiplatform.feature.customer.data.datasource.remote.CustomerRemoteDataSource
import com.example.kotlinmultiplatform.feature.customer.data.mapper.toDomain
import com.example.kotlinmultiplatform.feature.customer.data.mapper.toDTO
import com.example.kotlinmultiplatform.feature.customer.domain.model.Customer
import com.example.kotlinmultiplatform.feature.customer.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CustomerRepositoryImpl(
    private val dataSource: CustomerRemoteDataSource
) : CustomerRepository {

    override fun observeAll(): Flow<List<Customer>> = flow {
        emit(dataSource.getCustomers().map { it.toDomain() })
    }

    override suspend fun findById(id: String): Customer? =
        dataSource.getCustomers().map { it.toDomain() }.find { it.id == id }

    override suspend fun save(customer: Customer) {
        dataSource.saveCustomer(customer.toDTO())
    }

    override suspend fun update(customer: Customer) {
        dataSource.updateCustomer(customer.id, customer.toDTO())
    }

    override suspend fun deleteById(id: String) {
        dataSource.deleteCustomer(id)
    }
}
