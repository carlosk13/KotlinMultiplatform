package com.example.kotlinmultiplatform.di

import com.example.kotlinmultiplatform.core.constants.Constants
import com.example.kotlinmultiplatform.core.network.createHttpClient
import com.example.kotlinmultiplatform.feature.customer.data.datasource.remote.CustomerRemoteDataSource
import com.example.kotlinmultiplatform.feature.customer.data.remote.CustomerApi
import com.example.kotlinmultiplatform.feature.customer.data.repository.CustomerRepositoryImpl
import com.example.kotlinmultiplatform.feature.customer.domain.usecase.DeleteCustomerUseCase
import com.example.kotlinmultiplatform.feature.customer.domain.usecase.ObserveCustomersUseCase
import com.example.kotlinmultiplatform.feature.customer.domain.usecase.SaveCustomerUseCase
import com.example.kotlinmultiplatform.feature.customer.domain.usecase.UpdateCustomerUseCase
import com.example.kotlinmultiplatform.feature.customer.presentation.viewmodel.CustomerViewModel
import com.example.kotlinmultiplatform.feature.product.data.datasource.remote.ProductRemoteDataSource
import com.example.kotlinmultiplatform.feature.product.data.remote.ProductApi
import com.example.kotlinmultiplatform.feature.product.data.repository.ProductRepositoryImpl
import com.example.kotlinmultiplatform.feature.product.domain.usecase.DeleteProductUseCase
import com.example.kotlinmultiplatform.feature.product.domain.usecase.ObserveProductsUseCase
import com.example.kotlinmultiplatform.feature.product.domain.usecase.SaveProductUseCase
import com.example.kotlinmultiplatform.feature.product.domain.usecase.UpdateProductUseCase
import com.example.kotlinmultiplatform.feature.product.presentation.viewmodel.ProductViewModel

object AppComponent {
    private val httpClient by lazy { createHttpClient() }


    private val customerApi by lazy { CustomerApi(httpClient, Constants.BASE_URL) }
    private val customerDataSource by lazy { CustomerRemoteDataSource(customerApi) }
    private val customerRepository by lazy { CustomerRepositoryImpl(customerDataSource) }
    private val observeCustomers by lazy { ObserveCustomersUseCase(customerRepository) }
    private val saveCustomer by lazy { SaveCustomerUseCase(customerRepository) }
    private val updateCustomer by lazy { UpdateCustomerUseCase(customerRepository) }
    private val deleteCustomer by lazy { DeleteCustomerUseCase(customerRepository) }
    val customerViewModel by lazy { CustomerViewModel(observeCustomers, saveCustomer, updateCustomer, deleteCustomer) }


    private val productApi by lazy { ProductApi(httpClient, Constants.BASE_URL) }
    private val productDataSource by lazy { ProductRemoteDataSource(productApi) }
    private val productRepository by lazy { ProductRepositoryImpl(productDataSource) }
    private val observeProducts by lazy { ObserveProductsUseCase(productRepository) }
    private val saveProduct by lazy { SaveProductUseCase(productRepository) }
    private val updateProduct by lazy { UpdateProductUseCase(productRepository) }
    private val deleteProduct by lazy { DeleteProductUseCase(productRepository) }
    val productViewModel by lazy { ProductViewModel(observeProducts, saveProduct, updateProduct, deleteProduct) }
}
