package com.codeshinobi.expendituretracker.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codeshinobi.expendituretracker.ui.theme.ExpenditureTrackerTheme

@Composable
fun ExpendituresScreen(navController: NavController) {
    ExpenditureTrackerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
//                Box(
//                    modifier = Modifier.fillMaxWidth()
//                        .height(200.dp)
//                        .padding(horizontal = 15.dp, vertical = 10.dp)
//                        .clip(MaterialTheme.shapes.large)
//                ) {
//                    Image(
//                        painter = painterResource(R.drawable.images),
//                        contentDescription = "home_screen_bg",
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier.fillMaxSize()
//                    )
//                }
                Text(
                    "Expenditures Screen",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }
        }
    }
}