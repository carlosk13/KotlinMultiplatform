package com.example.kotlinmultiplatform.feature.product.domain.repository

import com.example.kotlinmultiplatform.feature.product.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun observeAll(): Flow<List<Product>>
    suspend fun findByCode(code: String): Product?
    suspend fun save(product: Product)
    suspend fun update(product: Product)
    suspend fun deleteByCode(code: String)
}
