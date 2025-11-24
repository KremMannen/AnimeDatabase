package com.example.eksamensapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.eksamensapp.data.database.UserIdeaEntity
import com.example.eksamensapp.ui.theme.DarkRed
import com.example.eksamensapp.ui.theme.DarkerRed
import com.example.eksamensapp.ui.theme.LightGrayBorderColor
import com.example.eksamensapp.ui.theme.SelectedButtonColor

@Composable
fun SearchIdeasItem(
    onSearchClick: (String) -> Unit,
    results: List<UserIdeaEntity>,
    itemContent: @Composable (UserIdeaEntity) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = searchText,
            onValueChange = {searchText = it},
            label = { Text("Søk etter ID eller Tittel", color = Color.DarkGray) },
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.LightGray,
                disabledContainerColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .border(
                    width = 1.dp,
                    color = SelectedButtonColor,
                )
        )

        Button(
            onClick = { onSearchClick(searchText) },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkerRed,
                contentColor = Color.White
            ),
            border = BorderStroke(1.dp, SelectedButtonColor),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp)
        ) {
            Text("Søk")
        }

        HorizontalDivider(
            thickness = 3.dp,
            color = LightGrayBorderColor
        )

        if (results.isEmpty()) {
            Text(
                text = "No results found.",
                color = Color.White,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
                items(results) { result ->
                    itemContent(result)
                }
            }
        }
    }
}