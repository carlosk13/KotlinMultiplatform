package com.example.kotlinmultiplatform.feature.customer.data.datasource.remote

import com.example.kotlinmultiplatform.feature.customer.data.dto.CustomerDTO
import com.example.kotlinmultiplatform.feature.customer.data.remote.CustomerApi

class CustomerRemoteDataSource(private val api: CustomerApi) {
    suspend fun getCustomers(): List<CustomerDTO> = api.getCustomers()
    suspend fun saveCustomer(dto: CustomerDTO) = api.saveCustomer(dto)
    suspend fun updateCustomer(id: String, dto: CustomerDTO) = api.updateCustomer(id, dto)
    suspend fun deleteCustomer(id: String) = api.deleteCustomer(id)
}

