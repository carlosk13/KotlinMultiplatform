package com.example.kotlinmultiplatform.feature.customer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmultiplatform.feature.customer.domain.usecase.DeleteCustomerUseCase
import com.example.kotlinmultiplatform.feature.customer.domain.usecase.ObserveCustomersUseCase
import com.example.kotlinmultiplatform.feature.customer.domain.usecase.SaveCustomerUseCase
import com.example.kotlinmultiplatform.feature.customer.domain.usecase.UpdateCustomerUseCase
import com.example.kotlinmultiplatform.feature.customer.presentation.event.CustomerEvent
import com.example.kotlinmultiplatform.feature.customer.presentation.state.CustomerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CustomerViewModel(
    private val observeCustomers: ObserveCustomersUseCase,
    private val saveCustomer: SaveCustomerUseCase,
    private val updateCustomer: UpdateCustomerUseCase,
    private val deleteCustomer: DeleteCustomerUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CustomerState())
    val state: StateFlow<CustomerState> = _state.asStateFlow()

    init {
        refresh()
    }

    fun onEvent(event: CustomerEvent) {
        when (event) {
            is CustomerEvent.LoadAll -> refresh()
            is CustomerEvent.Save -> viewModelScope.launch {
                try {
                    saveCustomer(event.customer)
                    refresh()
                } catch (e: Exception) {
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
            }
            is CustomerEvent.Update -> viewModelScope.launch {
                try {
                    updateCustomer(event.customer)
                    refresh()
                } catch (e: Exception) {
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
            }
            is CustomerEvent.Delete -> viewModelScope.launch {
                try {
                    deleteCustomer(event.id)
                    refresh()
                } catch (e: Exception) {
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
            }
        }
    }

    private fun refresh() {
        _state.update { it.copy(isLoading = true) }
        observeCustomers()
            .onEach { customers ->
                _state.update { it.copy(customers = customers, isLoading = false, error = null) }
            }
            .catch { e ->
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
            .launchIn(viewModelScope)
    }
}
