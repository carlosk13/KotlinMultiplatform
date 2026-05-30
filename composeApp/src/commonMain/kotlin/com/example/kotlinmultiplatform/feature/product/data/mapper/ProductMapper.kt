package com.example.kotlinmultiplatform.feature.product.data.mapper

import com.example.kotlinmultiplatform.feature.product.data.dto.ProductDTO
import com.example.kotlinmultiplatform.feature.product.domain.model.Product

fun ProductDTO.toDomain() = Product(
    code = code, description = description,
    category = category, price = price, stock = stock, taxable = taxable
)

fun Product.toDTO() = ProductDTO(
    code = code, description = description,
    category = category, price = price, stock = stock, taxable = taxable
)
