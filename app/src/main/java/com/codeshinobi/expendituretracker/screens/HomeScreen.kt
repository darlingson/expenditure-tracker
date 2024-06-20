package com.codeshinobi.expendituretracker.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.Keyboard
import compose.icons.fontawesomeicons.solid.CameraRetro
import compose.icons.fontawesomeicons.solid.PhotoVideo

data class HomeGridItemData(val name: String, val icon: ImageVector, val onClick: () -> Unit)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    ExpenditureTrackerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val items = listOf(
                HomeGridItemData(
                    name = "Scan Receipt",
                    icon = FontAwesomeIcons.Solid.CameraRetro,
                    onClick = { /* Navigate to camera screen */ }),
                HomeGridItemData(
                    name = "Gallery",
                    icon = FontAwesomeIcons.Solid.PhotoVideo,
                    onClick = { /* Navigate to gallery screen */ }),
                HomeGridItemData(
                    name = "Enter manually",
                    icon = FontAwesomeIcons.Regular.Keyboard,
                    onClick = { /* Navigate to manual entry screen */ })
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                Text(
                    text = "Expenditure Tracker",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 64.dp),
                    modifier = Modifier.padding(15.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(items.size) { index ->
                        val item = items[index]
                        HomeGridItem(name = item.name, icon = item.icon, onClick = item.onClick)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeGridItem(
    modifier: Modifier = Modifier,
    name: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = name,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(text = name, style = MaterialTheme.typography.bodyLarge)
    }
}
