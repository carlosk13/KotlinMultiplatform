package com.example.kotlinmultiplatform.feature.product.data.remote

import com.example.kotlinmultiplatform.feature.product.data.dto.ProductDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ProductApi(
    private val client: HttpClient,
    private val baseUrl: String
) {
    suspend fun getProducts(): List<ProductDTO> =
        client.get("$baseUrl/products").body()

    suspend fun saveProduct(dto: ProductDTO): ProductDTO =
        client.post("$baseUrl/products") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }.body()

    suspend fun updateProduct(code: String, dto: ProductDTO): ProductDTO =
        client.put("$baseUrl/products/$code") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }.body()

    suspend fun deleteProduct(code: String) {
        client.delete("$baseUrl/products/$code")
    }
}
