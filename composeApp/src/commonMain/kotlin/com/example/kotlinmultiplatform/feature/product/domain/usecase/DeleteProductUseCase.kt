package com.example.kotlinmultiplatform.feature.product.domain.usecase

import com.example.kotlinmultiplatform.feature.product.domain.repository.ProductRepository

class DeleteProductUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(code: String) = repository.deleteByCode(code)
}
