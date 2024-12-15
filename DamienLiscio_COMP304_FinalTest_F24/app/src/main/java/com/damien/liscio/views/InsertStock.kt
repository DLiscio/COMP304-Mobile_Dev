package com.damien.liscio.views

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.damien.liscio.viewmodel.StocksViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.damien.liscio.DisplayActivity
import com.damien.liscio.data.Stock
import com.damien.liscio.data.StockInfo
import kotlinx.serialization.json.Json

@Composable
fun InsertStockScreen(
    modifier: Modifier
) {
    val context = LocalContext.current
    val stocksViewModel: StocksViewModel = koinViewModel()
    val stocks by stocksViewModel.stockInfo.observeAsState(emptyList())
    var selectedStock by remember { mutableStateOf<StockInfo?>(null) }
    var stockSymbol by remember { mutableStateOf(TextFieldValue("")) }
    var companyName by remember { mutableStateOf(TextFieldValue("")) }
    var stockQuote by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Insert Stocks"
        )

        TextField(
            value = stockSymbol,
            onValueChange = { stockSymbol = it },
            label = { Text("Stock Symbol") }
        )

        TextField(
            value = companyName,
            onValueChange = { companyName = it },
            label = { Text("Company Name") }
        )

        TextField(
            value = stockQuote,
            onValueChange = { stockQuote = it },
            label = { Text("Stock Quote") }
        )

        Button(
            onClick = {
                val stock = StockInfo(
                    stockSymbol = stockSymbol.text,
                    companyName = companyName.text,
                    stockQuote = stockQuote.text.trim().toDoubleOrNull() ?: 0.0
                )
                stocksViewModel.addStock(stock)
                stockSymbol = TextFieldValue("")
                companyName = TextFieldValue("")
                stockQuote = TextFieldValue("")
            }
        ){
            Text("Insert Stock")
        }

        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item {
                Text(
                    text = "Display Stock Info"
                )
            }
            items(stocks) { stock ->
                StockListItem(
                    stock = stock,
                    isSelected = selectedStock == stock,
                    onStockClicked = {selectedStock = stock},
                    modifier = modifier.padding(horizontal = 20.dp)
                )
            }
            item{
                Button(
                    onClick = {
                        selectedStock?.let { stockInfo ->
                            val stock = stockInfo.toStock()
                            val jsonStock = Json.encodeToString(Stock.serializer(), stock)
                            val intentDisplayStock = Intent(context, DisplayActivity::class.java).apply {
                                putExtra("StockInfo", jsonStock)
                            }
                            context.startActivity(intentDisplayStock)
                        }
                    }
                ) {
                    Text("Display Stock Info")
                }
            }
        }
    }
}

@Composable
fun StockListItem(
    stock: StockInfo,
    isSelected: Boolean,
    onStockClicked: (StockInfo) -> Unit,
    modifier: Modifier
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onStockClicked(stock) }
            .background(backgroundColor)
            .padding(10.dp),
        horizontalAlignment = Alignment.Start
    ){
        Text(text = stock.stockSymbol)
    }
}

fun StockInfo.toStock(): Stock {
    return Stock(
        stockSymbol = this.stockSymbol,
        companyName = this.companyName,
        stockQuote = this.stockQuote
    )
}
