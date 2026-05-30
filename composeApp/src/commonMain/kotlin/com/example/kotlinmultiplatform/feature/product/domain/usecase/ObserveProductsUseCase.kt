package com.example.kotlinmultiplatform.feature.product.domain.usecase

import com.example.kotlinmultiplatform.feature.product.domain.model.Product
import com.example.kotlinmultiplatform.feature.product.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ObserveProductsUseCase(private val repository: ProductRepository) {
    operator fun invoke(): Flow<List<Product>> = repository.observeAll()
}
