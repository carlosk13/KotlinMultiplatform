package com.example.kotlinmultiplatform.feature.product.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val code: String = "",
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val stock: Int = 0,
    val taxable: Boolean = true
)
