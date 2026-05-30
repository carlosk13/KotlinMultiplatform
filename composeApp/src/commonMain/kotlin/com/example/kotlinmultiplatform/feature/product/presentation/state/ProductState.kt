package com.example.kotlinmultiplatform.feature.product.presentation.state

import com.example.kotlinmultiplatform.feature.product.domain.model.Product

data class ProductState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
