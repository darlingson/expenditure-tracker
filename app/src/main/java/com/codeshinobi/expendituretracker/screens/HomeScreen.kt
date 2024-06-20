package com.codeshinobi.expendituretracker.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codeshinobi.expendituretracker.ui.theme.ExpenditureTrackerTheme
import compose.icons.AllIcons
import compose.icons.FontAwesomeIcons
import compose.icons.SimpleIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.Keyboard
import compose.icons.fontawesomeicons.solid.CameraRetro
import compose.icons.fontawesomeicons.solid.PhotoVideo
import compose.icons.simpleicons.Icon

data class HomeGridItemData(val name: String, val icon: ImageVector)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    ExpenditureTrackerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val items = listOf(
                HomeGridItemData(name = "Expenses", icon = FontAwesomeIcons.Solid.CameraRetro),
                HomeGridItemData(name = "Income", icon = FontAwesomeIcons.Solid.PhotoVideo),
                HomeGridItemData(name = "Balance", icon = FontAwesomeIcons.Regular.Keyboard)
            )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                modifier = Modifier.padding(15.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(items.size) { index ->
                    val item = items[index]
                    HomeGridItem(name = item.name, icon = item.icon)
                }
            }
        }
    }
}

@Composable
fun HomeGridItem(modifier: Modifier = Modifier, name: String, icon: ImageVector) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = icon, contentDescription = name, modifier = Modifier.padding(bottom = 8.dp))
        Text(text = name, style = MaterialTheme.typography.bodyLarge)
    }
}
