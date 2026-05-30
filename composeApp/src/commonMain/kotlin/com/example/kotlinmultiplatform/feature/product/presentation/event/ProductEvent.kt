package com.example.kotlinmultiplatform.feature.product.presentation.event

import com.example.kotlinmultiplatform.feature.product.domain.model.Product

sealed class ProductEvent {
    data class Save(val product: Product) : ProductEvent()
    data class Update(val product: Product) : ProductEvent()
    data class Delete(val code: String) : ProductEvent()
}
