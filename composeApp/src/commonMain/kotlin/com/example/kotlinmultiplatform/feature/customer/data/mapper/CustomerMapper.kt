package com.example.kotlinmultiplatform.feature.customer.data.mapper

import com.example.kotlinmultiplatform.feature.customer.data.dto.CustomerDTO
import com.example.kotlinmultiplatform.feature.customer.domain.model.Customer

fun CustomerDTO.toDomain() = Customer(id = id, name = name,
    email = email, purchaseHistory = purchaseHistory)
fun Customer.toDTO() = CustomerDTO(id = id, name = name,
    email = email, purchaseHistory = purchaseHistory)
