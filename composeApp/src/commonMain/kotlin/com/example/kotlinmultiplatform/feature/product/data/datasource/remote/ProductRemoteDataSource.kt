package com.example.kotlinmultiplatform.feature.product.data.datasource.remote

import com.example.kotlinmultiplatform.feature.product.data.dto.ProductDTO
import com.example.kotlinmultiplatform.feature.product.data.remote.ProductApi

class ProductRemoteDataSource(private val api: ProductApi) {
    suspend fun getProducts(): List<ProductDTO> = api.getProducts()
    suspend fun saveProduct(dto: ProductDTO) = api.saveProduct(dto)
    suspend fun updateProduct(code: String, dto: ProductDTO) = api.updateProduct(code, dto)
    suspend fun deleteProduct(code: String) = api.deleteProduct(code)
}
