package com.example.lab13

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

sealed interface ScreenState {
    //Estados de la pantalla
    object Cargando : ScreenState
    data class Contenido(val data: String) : ScreenState
    data class Error(val message: String) : ScreenState
}

@Composable
fun ContenidoAnimacion() {
    //Estado actual de la pantalla
    var currentState by remember { mutableStateOf<ScreenState>(ScreenState.Cargando) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = { currentState = ScreenState.Cargando }) {
                Text("Cargando")
            }
            OutlinedButton(onClick = {
                currentState = ScreenState.Contenido("¡Datos cargados con éxito!")
            }) {
                Text("Éxito")
            }
            OutlinedButton(onClick = {
                currentState = ScreenState.Error("Error 500: Sin conexión al servidor")
            }) {
                Text("Error")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedContent(
            targetState = currentState,
            transitionSpec = {
                // Entrada en 700 milisegundos y salida en 4000 milisegundos
                fadeIn(animationSpec = tween(durationMillis = 700)) togetherWith
                        fadeOut(animationSpec = tween(durationMillis = 400))
            },
            label = "StateTransition"
        ) { targetState ->
            // Renderizado de la UI según el estado activo
            when (targetState) {
                is ScreenState.Cargando -> CargandoView()
                is ScreenState.Contenido -> ContenidoView(message = targetState.data)
                is ScreenState.Error -> ErrorView(errorMessage = targetState.message)
            }
        }
    }
}

@Composable
private fun CargandoView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp)
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Cargando información...", fontSize = 16.sp, color = Color.Gray)
    }
}

@Composable
private fun ContenidoView(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(12.dp))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Exito", fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = message, color = Color(0xFF1B5E20))
        }
    }
}

@Composable
private fun ErrorView(errorMessage: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFEBEE), shape = RoundedCornerShape(12.dp))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Ocurrió un problema", fontWeight = FontWeight.Bold, color = Color(0xFFC62828), fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage, color = Color(0xFFB71C1C))
        }
    }
}