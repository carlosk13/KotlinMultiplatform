package com.example.kotlinmultiplatform.feature.product.data.repository

import com.example.kotlinmultiplatform.feature.product.data.datasource.remote.ProductRemoteDataSource
import com.example.kotlinmultiplatform.feature.product.data.mapper.toDomain
import com.example.kotlinmultiplatform.feature.product.data.mapper.toDTO
import com.example.kotlinmultiplatform.feature.product.domain.model.Product
import com.example.kotlinmultiplatform.feature.product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryImpl(
    private val dataSource: ProductRemoteDataSource
) : ProductRepository {

    override fun observeAll(): Flow<List<Product>> = flow {
        emit(dataSource.getProducts().map { it.toDomain() })
    }

    override suspend fun findByCode(code: String): Product? =
        dataSource.getProducts().map { it.toDomain() }.find { it.code == code }

    override suspend fun save(product: Product) {
        dataSource.saveProduct(product.toDTO())
    }

    override suspend fun update(product: Product) {
        dataSource.updateProduct(product.code, product.toDTO())
    }

    override suspend fun deleteByCode(code: String) {
        dataSource.deleteProduct(code)
    }
}
