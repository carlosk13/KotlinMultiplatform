package com.example.kotlinmultiplatform.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.kotlinmultiplatform.di.AppComponent
import com.example.kotlinmultiplatform.feature.customer.domain.model.Customer
import com.example.kotlinmultiplatform.feature.customer.presentation.screen.CreateCustomerScreen
import com.example.kotlinmultiplatform.feature.customer.presentation.screen.ListCustomerScreen
import com.example.kotlinmultiplatform.feature.customer.presentation.screen.UpdateCustomerScreen
import com.example.kotlinmultiplatform.feature.product.domain.model.Product
import com.example.kotlinmultiplatform.feature.product.presentation.screen.CreateProductScreen
import com.example.kotlinmultiplatform.feature.product.presentation.screen.ListProductScreen
import com.example.kotlinmultiplatform.feature.product.presentation.screen.UpdateProductScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Products : BottomNavItem("product_list", "Productos", Icons.Default.ShoppingCart)
    object Customers : BottomNavItem("customer_list", "Clientes", Icons.Default.Person)
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val items = listOf(BottomNavItem.Products, BottomNavItem.Customers)

    val customerViewModel = AppComponent.customerViewModel
    val productViewModel = AppComponent.productViewModel

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(BottomNavItem.Products.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Products.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("product_list") {
                ListProductScreen(
                    viewModel = productViewModel,
                    onNavigateToCreate = { navController.navigate("create_product") },
                    onNavigateToEdit = { product ->
                        navController.currentBackStackEntry?.savedStateHandle
                            ?.set("product", Json.encodeToString(product))
                        navController.navigate("update_product")
                    }
                )
            }
            composable("create_product") {
                CreateProductScreen(
                    viewModel = productViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("update_product") {
                val product = navController.previousBackStackEntry
                    ?.savedStateHandle?.get<String>("product")
                    ?.let { Json.decodeFromString<Product>(it) }
                if (product != null) {
                    UpdateProductScreen(
                        viewModel = productViewModel,
                        product = product,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
            composable("customer_list") {
                ListCustomerScreen(
                    viewModel = customerViewModel,
                    onNavigateToCreate = { navController.navigate("create_customer") },
                    onNavigateToEdit = { customer ->
                        navController.currentBackStackEntry?.savedStateHandle
                            ?.set("customer", Json.encodeToString(customer))
                        navController.navigate("update_customer")
                    }
                )
            }
            composable("create_customer") {
                CreateCustomerScreen(
                    viewModel = customerViewModel,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            composable("update_customer") {
                val customer = navController.previousBackStackEntry
                    ?.savedStateHandle?.get<String>("customer")
                    ?.let { Json.decodeFromString<Customer>(it) }
                if (customer != null) {
                    UpdateCustomerScreen(
                        viewModel = customerViewModel,
                        customer = customer,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
