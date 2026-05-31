package com.example.kotlinmultiplatform.feature.customer.data.remote

import com.example.kotlinmultiplatform.feature.customer.data.dto.CustomerDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class CustomerApi(
    private val client: HttpClient,
    private val baseUrl: String
) {
    suspend fun getCustomers(): List<CustomerDTO> =
        client.get("$baseUrl/customers").body()
    suspend fun saveCustomer(dto: CustomerDTO) {
        client.post("$baseUrl/customers") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }
    }
    suspend fun updateCustomer(id: String, dto: CustomerDTO) {
        client.put("$baseUrl/customers/$id") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }
    }

    suspend fun deleteCustomer(id: String) {
        client.delete("$baseUrl/customers/$id")
    }
}
