package com.example.eksamensapp.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eksamensapp.ui.theme.AppBackgroundColor
import com.example.eksamensapp.ui.theme.RedBackgroundColor
import com.example.eksamensapp.ui.theme.SelectedBackgroundColor
import com.example.eksamensapp.ui.theme.UnselectedBackgroundColor
import com.example.eksamensapp.ui.theme.SelectedBorderColor
import com.example.eksamensapp.ui.theme.UnselectedBorderColor
import com.example.eksamensapp.ui.theme.SelectedTextColor
import com.example.eksamensapp.ui.theme.UnselectedTextColor

@Composable
fun AppHeader(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(RedBackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 60.dp)
        )
    }
}

