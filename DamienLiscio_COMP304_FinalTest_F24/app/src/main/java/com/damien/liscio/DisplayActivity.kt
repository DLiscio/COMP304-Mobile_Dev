package com.damien.liscio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.damien.liscio.ui.theme.DamienLiscio_COMP304_FinalTest_F24Theme
import com.damien.liscio.views.DisplayStockScreen
import androidx.compose.material3.Text
import com.damien.liscio.data.Stock
import kotlinx.serialization.json.Json

class DisplayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val jsonStock = intent.getStringExtra("StockInfo")
        val stock = jsonStock?.let { Json.decodeFromString<Stock>(it) }
        setContent {
            DamienLiscio_COMP304_FinalTest_F24Theme {
                Scaffold(
                    content = { padding ->
                        stock?.let {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(padding),
                            ) {
                                DisplayStockScreen(
                                    stock = it,
                                    modifier = Modifier
                                )
                            }
                        } ?: run{
                            Text("No Stock Info")
                        }
                    }
                )
            }
        }
    }
}