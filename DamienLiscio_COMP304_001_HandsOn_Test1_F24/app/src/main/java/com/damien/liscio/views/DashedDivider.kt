package com.damien.liscio.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DashedDivider() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(4.dp)
            .padding(vertical = 4.dp)
    ) {
        val dashWidth = 4.dp.toPx()
        val spaceWidth = 4.dp.toPx()
        val totalWidth = size.width

        var x = 0f
        while (x < totalWidth) {
            drawLine(
                color = Color.Black,
                start = androidx.compose.ui.geometry.Offset(x, 0f),
                end = androidx.compose.ui.geometry.Offset(x + dashWidth, 0f),
                strokeWidth = 3f
            )
            x += dashWidth + spaceWidth
        }
    }
}