package com.example.kotlinmultiplatform.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.layout.padding
import com.example.kotlinmultiplatform.di.AppComponent
import com.example.kotlinmultiplatform.feature.customer.domain.model.Customer
import com.example.kotlinmultiplatform.feature.customer.presentation.screen.CreateCustomerScreen
import com.example.kotlinmultiplatform.feature.customer.presentation.screen.ListCustomerScreen
import com.example.kotlinmultiplatform.feature.customer.presentation.screen.UpdateCustomerScreen
import com.example.kotlinmultiplatform.feature.product.domain.model.Product
import com.example.kotlinmultiplatform.feature.product.presentation.screen.CreateProductScreen
import com.example.kotlinmultiplatform.feature.product.presentation.screen.ListProductScreen
import com.example.kotlinmultiplatform.feature.product.presentation.screen.UpdateProductScreen

private sealed class AppScreen {
    object Products : AppScreen()
    object Customers : AppScreen()
    object CreateProduct : AppScreen()
    data class UpdateProduct(val product: Product) : AppScreen()
    object CreateCustomer : AppScreen()
    data class UpdateCustomer(val customer: Customer) : AppScreen()
}

private sealed class BottomNavItem(val title: String, val icon: ImageVector) {
    object Products : BottomNavItem("Productos", Icons.Default.ShoppingCart)
    object Customers : BottomNavItem("Clientes", Icons.Default.Person)
}

@Composable
fun AppNavigation() {
    val screenStack = remember { mutableStateListOf<AppScreen>(AppScreen.Products) }
    val currentScreen = screenStack.lastOrNull() ?: AppScreen.Products
    val customerViewModel = AppComponent.customerViewModel
    val productViewModel = AppComponent.productViewModel

    val onNavigateToCreate: (() -> Unit)? = when (currentScreen) {
        is AppScreen.Products -> ({ screenStack.add(AppScreen.CreateProduct) })
        is AppScreen.Customers -> ({ screenStack.add(AppScreen.CreateCustomer) })
        else -> null
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val screen = currentScreen
                val items = listOf(BottomNavItem.Products, BottomNavItem.Customers)

                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = isTabSelected(item, screen),
                        onClick = {
                            if (item is BottomNavItem.Products) {
                                screenStack.clear()
                                screenStack.add(AppScreen.Products)
                            } else {
                                screenStack.clear()
                                screenStack.add(AppScreen.Customers)
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            onNavigateToCreate?.let { navigate ->
                FloatingActionButton(onClick = navigate) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                }
            }
        }
    ) { paddingValues ->
        when (val screen = currentScreen) {
            is AppScreen.Products -> {
                ListProductScreen(
                    viewModel = productViewModel,
                    modifier = Modifier.padding(paddingValues),
                    onNavigateToEdit = { product ->
                        screenStack.add(AppScreen.UpdateProduct(product))
                    }
                )
            }
            is AppScreen.CreateProduct -> {
                CreateProductScreen(
                    viewModel = productViewModel,
                    modifier = Modifier.padding(paddingValues),
                    onNavigateBack = { popBack(screenStack) }
                )
            }
            is AppScreen.UpdateProduct -> {
                UpdateProductScreen(
                    viewModel = productViewModel,
                    product = screen.product,
                    modifier = Modifier.padding(paddingValues),
                    onNavigateBack = { popBack(screenStack) }
                )
            }
            is AppScreen.Customers -> {
                ListCustomerScreen(
                    viewModel = customerViewModel,
                    modifier = Modifier.padding(paddingValues),
                    onNavigateToEdit = { customer ->
                        screenStack.add(AppScreen.UpdateCustomer(customer))
                    }
                )
            }
            is AppScreen.CreateCustomer -> {
                CreateCustomerScreen(
                    viewModel = customerViewModel,
                    modifier = Modifier.padding(paddingValues),
                    onNavigateBack = { popBack(screenStack) }
                )
            }
            is AppScreen.UpdateCustomer -> {
                UpdateCustomerScreen(
                    viewModel = customerViewModel,
                    customer = screen.customer,
                    modifier = Modifier.padding(paddingValues),
                    onNavigateBack = { popBack(screenStack) }
                )
            }
        }
    }
}

private fun isTabSelected(item: BottomNavItem, screen: AppScreen): Boolean =
    when (item) {
        BottomNavItem.Products -> screen is AppScreen.Products || screen is AppScreen.CreateProduct || screen is AppScreen.UpdateProduct
        BottomNavItem.Customers -> screen is AppScreen.Customers || screen is AppScreen.CreateCustomer || screen is AppScreen.UpdateCustomer
    }

private fun popBack(screenStack: SnapshotStateList<AppScreen>) {
    if (screenStack.size > 1) {
        screenStack.removeLast()
    }
}
