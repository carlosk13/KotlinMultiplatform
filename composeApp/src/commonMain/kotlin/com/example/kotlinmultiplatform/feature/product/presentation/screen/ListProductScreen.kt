package com.example.kotlinmultiplatform.feature.product.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kotlinmultiplatform.feature.product.domain.model.Product
import com.example.kotlinmultiplatform.feature.product.presentation.component.EmptyProductView
import com.example.kotlinmultiplatform.feature.product.presentation.component.ProductList
import com.example.kotlinmultiplatform.feature.product.presentation.event.ProductEvent
import com.example.kotlinmultiplatform.feature.product.presentation.viewmodel.ProductViewModel

@Composable
fun ListProductScreen(
    viewModel: ProductViewModel,
    modifier: Modifier = Modifier,
    onNavigateToEdit: (Product) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(ProductEvent.LoadAll)
    }

    Box(modifier = modifier) {
        when {
            state.isLoading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

            state.products.isEmpty() -> EmptyProductView()

            else -> ProductList(
                products = state.products,
                onEdit = onNavigateToEdit,
                onDelete = { code -> viewModel.onEvent(ProductEvent.Delete(code)) }
            )
        }
    }
}
