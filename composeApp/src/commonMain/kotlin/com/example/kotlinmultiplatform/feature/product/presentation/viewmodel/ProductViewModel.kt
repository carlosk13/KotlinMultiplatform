package com.example.kotlinmultiplatform.feature.product.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmultiplatform.feature.product.domain.usecase.DeleteProductUseCase
import com.example.kotlinmultiplatform.feature.product.domain.usecase.ObserveProductsUseCase
import com.example.kotlinmultiplatform.feature.product.domain.usecase.SaveProductUseCase
import com.example.kotlinmultiplatform.feature.product.domain.usecase.UpdateProductUseCase
import com.example.kotlinmultiplatform.feature.product.presentation.event.ProductEvent
import com.example.kotlinmultiplatform.feature.product.presentation.state.ProductState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val observeProducts: ObserveProductsUseCase,
    private val saveProduct: SaveProductUseCase,
    private val updateProduct: UpdateProductUseCase,
    private val deleteProduct: DeleteProductUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductState())
    val state: StateFlow<ProductState> = _state.asStateFlow()

    init {
        refresh()
    }

    fun onEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.Save -> viewModelScope.launch {
                try {
                    saveProduct(event.product)
                    refresh()
                } catch (e: Exception) {
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
            }
            is ProductEvent.Update -> viewModelScope.launch {
                try {
                    updateProduct(event.product)
                    refresh()
                } catch (e: Exception) {
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
            }
            is ProductEvent.Delete -> viewModelScope.launch {
                try {
                    deleteProduct(event.code)
                    refresh()
                } catch (e: Exception) {
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
            }
        }
    }

    private fun refresh() {
        _state.update { it.copy(isLoading = true) }
        observeProducts()
            .onEach { products ->
                _state.update { it.copy(products = products, isLoading = false, error = null) }
            }
            .catch { e ->
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
            .launchIn(viewModelScope)
    }
}
