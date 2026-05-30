package com.example.kotlinmultiplatform.feature.product.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    val code: String = "",
    val description: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val stock: Int = 0,
    val taxable: Boolean = true
)
