package com.example.kotlinmultiplatform.feature.customer.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CustomerDTO(
    val id: String,
    val name: String,
    val email: String,
    val purchaseHistory: List<String> = emptyList()
)
