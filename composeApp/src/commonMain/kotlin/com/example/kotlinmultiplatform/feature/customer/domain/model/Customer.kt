package com.example.kotlinmultiplatform.feature.customer.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val purchaseHistory: List<String> = emptyList()
)
