package com.example.lab13

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
fun ColorAnimacion() {
    var isBlue by remember { mutableStateOf(true) }

    //Determina el color de destino segun el estado actual
    val targetColor = if (isBlue) Color(0xFF2196F3) else Color(0xFF4CAF50)

    //Animación del color con AnimationSpec
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        //Duracion en milisegundos
        animationSpec = tween(durationMillis = 600),
        label = "BoxColorAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { isBlue = !isBlue }) {
            Text(if (isBlue) "Cambiar a Verde" else "Cambiar a Azul")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .size(160.dp)
                .background(
                    color = animatedColor,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}