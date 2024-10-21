//301237966

package com.damien.liscio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.damien.liscio.ui.theme.DamienLiscio_COMP304_001_HandsOn_Test1_F24Theme
import com.damien.liscio.views.ContactList
import com.damien.liscio.views.AddContact

@OptIn(ExperimentalMaterial3Api::class)
class LiscioActivity : ComponentActivity() {
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
                        Column(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AddContact(
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            ContactList(
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                )
            }
        }
    }
}
