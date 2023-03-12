package com.example.marvel.presentation.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.marvel.R

@Composable
fun EmptyList(modifier: Modifier, onClick: () -> Unit) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_placeholder_emty_list),
            contentDescription = "",
            modifier = Modifier.size(150.dp),
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Text(text = "No se pudo obtener la información")
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        )
        TextButton(onClick = { onClick()}) {
            Text(text = "Volver a Intentar")
        }
    }
}