package com.damien.damienliscio_comp304lab3_ex1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.damien.damienliscio_comp304lab3_ex1.ui.theme.DamienLiscio_COMP304Lab3_Ex1Theme
import com.damien.damienliscio_comp304lab3_ex1.views.DashedDivider
import com.damien.damienliscio_comp304lab3_ex1.views.SavedLocations

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DamienLiscio_COMP304Lab3_Ex1Theme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            {
                                Column(
                                    modifier = Modifier.fillMaxSize()
                                            .padding(10.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Text(text = "TempTracker", fontSize = 24.sp)
                                    Text(text = "Saved Locations", fontSize = 16.sp)
                                }
                            }
                        )
                    }, content = { innerPadding ->
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            DashedDivider()
                            SavedLocations(modifier = Modifier
                                .fillMaxWidth())
                        }
                    },
                    bottomBar = {
                        val context = LocalContext.current
                        BottomAppBar(
                            modifier = Modifier.fillMaxWidth(),
                            content = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ){
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            imageVector = Icons.Default.Home,
                                            contentDescription = "Home",
                                            modifier = Modifier
                                                .size(30.dp)
                                                .clickable { val intentHome = Intent(context, MainActivity::class.java)
                                                    context.startActivity(intentHome) }
                                        )
                                        Text(text = "Home", fontSize = 12.sp)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Icon(
                                            imageVector = Icons.Default.AddCircle,
                                            contentDescription = "Add New Location",
                                            modifier = Modifier
                                                .size(30.dp)
                                                .clickable { val intentAddLocation = Intent(context, AddLocationActivity::class.java)
                                                    context.startActivity(intentAddLocation) }
                                        )
                                        Text(text = "Add New Location", fontSize = 12.sp)
                                    }
                                }
                            }
                        )
                    }
                )
            }
        }
    }
}

