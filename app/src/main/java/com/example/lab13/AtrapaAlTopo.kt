package com.example.lab13

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun AtrapaAlTopo() {
    var score by remember { mutableIntStateOf(0) }
    var timeLeft by remember { mutableIntStateOf(30) }
    var isPlaying by remember { mutableStateOf(false) }
    var activeMoleIndex by remember { mutableIntStateOf(-1) }

    //Contador del tiempo de partida (30 segundos)
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            score = 0
            timeLeft = 30
            activeMoleIndex = -1
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
            isPlaying = false
            activeMoleIndex = -1
        }
    }

    //Bucle de aparicion aleatoria de topos
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (timeLeft > 0) {
                val nextHole = Random.nextInt(0, 9)
                activeMoleIndex = nextHole
                //El topo permanece visible entre 600 ms y 1100 ms
                delay(Random.nextLong(600, 1100))
                activeMoleIndex = -1
                //Pausa antes de que salga el siguiente
                delay(250)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        //Marcador de puntos y tiempo
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Puntos: $score",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Tiempo: ${timeLeft}s",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = if (timeLeft <= 5) Color.Red else Color.Gray
            )
        }

        //Mapa de 3x3
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (row in 0..2) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    for (col in 0..2) {
                        val holeIndex = row * 3 + col
                        HoleView(
                            isActive = activeMoleIndex == holeIndex,
                            onMoleClick = {
                                if (isPlaying && activeMoleIndex == holeIndex) {
                                    score += 10
                                    activeMoleIndex = -1 // Oculta el topo al tocarlo
                                }
                            }
                        )
                    }
                }
            }
        }

        //Boton de control
        Button(
            onClick = { if (!isPlaying) isPlaying = true },
            enabled = !isPlaying,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = if (isPlaying) "¡En juego!" else if (timeLeft == 0) "Volver a jugar" else "Iniciar Juego",
                fontSize = 18.sp
            )
        }
    }
}

@Composable
private fun HoleView(
    isActive: Boolean,
    onMoleClick: () -> Unit
) {
    //Transición de color al estar activo
    val holeBorderColor by animateColorAsState(
        targetValue = if (isActive) Color(0xFFFFB74D) else Color(0xFF3E2723),
        animationSpec = tween(durationMillis = 200),
        label = "HoleBorder"
    )

    //Base de la madriguera del topo
    Box(
        modifier = Modifier
            .size(90.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(holeBorderColor)
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFF21130D)),
            contentAlignment = Alignment.Center
        ) {
            //Animacion de entrada/salida combinando escala y opacidad
            AnimatedVisibility(
                visible = isActive,
                enter = scaleIn(animationSpec = tween(180)) + fadeIn(animationSpec = tween(180)),
                exit = scaleOut(animationSpec = tween(150)) + fadeOut(animationSpec = tween(150))
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFF9800))
                        .clickable { onMoleClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🐹",
                        fontSize = 32.sp
                    )
                }
            }
        }
    }
}