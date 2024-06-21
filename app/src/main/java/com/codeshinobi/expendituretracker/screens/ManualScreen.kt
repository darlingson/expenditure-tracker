package com.codeshinobi.expendituretracker.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codeshinobi.expendituretracker.Greeting
import com.codeshinobi.expendituretracker.ui.theme.ExpenditureTrackerTheme
import java.util.Calendar

@Composable
fun ManualScreen(
    navController: androidx.navigation.NavController
) {
    ExpenditureTrackerTheme {
        addExpenditureForm()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addExpenditureForm(){
    var item by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
                .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val state = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
        DatePicker(state = state, modifier = Modifier.padding(16.dp))
        TextField(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            value = item,
            onValueChange = { item = it },
            label = { Text("Item") },
            )
        TextField(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            )
        TextField(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            shape = RoundedCornerShape(8.dp),
        )
        TextButton(
            shape = RoundedCornerShape(8.dp),
            onClick = { /* Do something! */ },
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Text("Save Expenditure")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    addExpenditureForm()
}