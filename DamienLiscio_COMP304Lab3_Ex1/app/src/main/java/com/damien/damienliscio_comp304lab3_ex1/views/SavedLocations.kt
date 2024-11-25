package com.damien.damienliscio_comp304lab3_ex1.views

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.damien.damienliscio_comp304lab3_ex1.viewModel.SavedLocationsViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.damien.damienliscio_comp304lab3_ex1.MainActivity
import com.damien.damienliscio_comp304lab3_ex1.ViewLocationActivity

@Composable
fun SavedLocations(modifier: Modifier) {
    val savedLocationsViewModel: SavedLocationsViewModel = koinViewModel()
    val savedLocations = savedLocationsViewModel.savedLocations.observeAsState(emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    SnackbarHost(
        hostState = snackbarHostState
    )

    LaunchedEffect(savedLocations.value) {
        val latestLocation = savedLocations.value.lastOrNull()
        latestLocation?.let { location ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "Added Location: ${location.savedLocation}"
                )
            }
        }
    }

    val context = LocalContext.current
    LazyColumn(
        modifier = modifier
    ) {
        items(savedLocations.value) { location ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable { val intentViewWeather = Intent(context, ViewLocationActivity::class.java)
                        context.startActivity(intentViewWeather) },
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Name: ${location.savedLocation}")
                DashedDivider()
            }
        }
    }
}