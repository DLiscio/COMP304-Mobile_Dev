package com.damien.liscio.views

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import com.damien.liscio.DamienActivity
import com.damien.liscio.data.Stock

@Composable
fun DisplayStockScreen(stock: Stock, modifier: Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "Stock Symbol: ${stock.stockSymbol}")
        Text(text = "Company Name: ${stock.companyName}")
        Text(text = "Stock Quote: ${stock.stockQuote}")
        Button(
            onClick = { val intentDisplayStock = Intent(context, DamienActivity::class.java)
                context.startActivity(intentDisplayStock)
            },
            modifier = modifier
                .padding(horizontal = 10.dp)
        ) {
            Text("Back")
        }
    }
}