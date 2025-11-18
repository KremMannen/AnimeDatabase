package com.example.eksamensapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.room.data.Car

@Composable
fun CarDetailsItem(
    car: Car,
    goBack: ( () -> Unit )? = null
)
{
    val (id, brand, model) = car

    Column {
        Text(brand)
        Text(id.toString())
        Text(model.toString())

        if (goBack != null) {
            Button(
                onClick = goBack
            ) {
                Text("Tilbake")
            }
        }
    }
}