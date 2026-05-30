package com.example.kotlinmultiplatform.feature.product.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.kotlinmultiplatform.feature.product.domain.model.Product

@Composable
fun ProductList(
    products: List<Product>,
    onEdit: (Product) -> Unit,
    onDelete: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products, key = { it.code }) { product ->
            ProductItem(product = product, onEdit = onEdit, onDelete = onDelete)
        }
    }
}
