package com.example.lab13

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TamYPosAnimacion() {
    var isExpandedAndMoved by remember { mutableStateOf(false) }

    // Transicion de tamaño y desplazamiento con animateDpAsState
    val boxSize by animateDpAsState(
        targetValue = if (isExpandedAndMoved) 180.dp else 90.dp,
        animationSpec = tween(durationMillis = 500),
        label = "BoxSize"
    )
    val offsetX by animateDpAsState(
        targetValue = if (isExpandedAndMoved) 80.dp else 0.dp,
        animationSpec = tween(durationMillis = 500),
        label = "OffsetX"
    )
    val offsetY by animateDpAsState(
        targetValue = if (isExpandedAndMoved) 60.dp else 0.dp,
        animationSpec = tween(durationMillis = 500),
        label = "OffsetY"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { isExpandedAndMoved = !isExpandedAndMoved }) {
            Text(if (isExpandedAndMoved) "Restablecer" else "Mover y Agrandar")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .offset(x = offsetX, y = offsetY) // Desplaza el punto de origen
                .size(boxSize)                   // Define las dimensiones
                .background(                      // Dibuja el fondo en la nueva posición y tamaño
                    color = Color(0xFFFF5722),
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}