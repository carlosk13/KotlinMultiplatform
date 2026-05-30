package com.example.kotlinmultiplatform.feature.product.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
    onNavigateToCreate: () -> Unit,
    onNavigateToEdit: (Product) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
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
}
