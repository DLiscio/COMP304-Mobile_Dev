package com.damien.liscio.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    @SerialName("stockSymbol")
    val stockSymbol: String,
    @SerialName("companyName")
    val companyName: String,
    @SerialName("stockQuote")
    val stockQuote: Double
)