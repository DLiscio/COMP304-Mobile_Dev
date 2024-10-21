//301237966

package com.damien.liscio

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.damien.liscio.ui.theme.DamienLiscio_COMP304_001_HandsOn_Test1_F24Theme

//Main Activity
@OptIn(ExperimentalMaterial3Api::class)
class DamienActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DamienLiscio_COMP304_001_HandsOn_Test1_F24Theme {
                Scaffold(
                    topBar = {
                        val isDark = isSystemInDarkTheme()
                        val imagePath = if (isDark) {
                            R.drawable.logo_dark
                        } else {
                            R.drawable.logo_light
                        }
                        TopAppBar(
                            title = {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ){
                                    Image(
                                        painter = painterResource(id = imagePath),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(150.dp)
                                    )
                                }
                            }
                        )
                    }, content = { innerPadding ->
                        val context = LocalContext.current
                        val isDark = isSystemInDarkTheme()
                        val imagePath = if (isDark) {
                            R.drawable.add_contact_dark
                        } else {
                            R.drawable.add_contact_light
                        }
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Image(
                                painter = painterResource(id = imagePath),
                                contentDescription = "Add New Contact",
                                modifier = Modifier
                                    .size(150.dp)
                                    .clickable {
                                        val intentAddContact = Intent(context, LiscioActivity::class.java)
                                        context.startActivity(intentAddContact)
                                    }
                            )
                            Text(
                                text = "Add New Contact",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}

