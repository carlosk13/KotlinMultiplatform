package com.example.kotlinmultiplatform.feature.product.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlinmultiplatform.feature.product.domain.model.Product
import com.example.kotlinmultiplatform.feature.product.presentation.event.ProductEvent
import com.example.kotlinmultiplatform.feature.product.presentation.viewmodel.ProductViewModel

@Composable
fun UpdateProductScreen(
    viewModel: ProductViewModel,
    product: Product,
    onNavigateBack: () -> Unit
) {
    var description by remember { mutableStateOf(product.description) }
    var category by remember { mutableStateOf(product.category) }
    var price by remember { mutableStateOf(product.price.toString()) }
    var stock by remember { mutableStateOf(product.stock.toString()) }
    var taxable by remember { mutableStateOf(product.taxable) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(value = product.code, onValueChange = {}, label = { Text("Código") }, enabled = false, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
            OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Categoría") }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
            OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Precio") }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
            OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock") }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Checkbox(checked = taxable, onCheckedChange = { taxable = it })
                Text("Gravable (taxable)")
            }
            Button(
                onClick = {
                    viewModel.onEvent(ProductEvent.Update(Product(
                        code = product.code, description = description,
                        category = category, price = price.toDoubleOrNull() ?: 0.0,
                        stock = stock.toIntOrNull() ?: 0, taxable = taxable
                    )))
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) { Text("Actualizar") }
        }
    }
}
