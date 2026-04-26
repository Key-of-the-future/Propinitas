package com.example.propinitas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.dp
import com.example.propinitas.ui.theme.PropinitasTheme
import com.example.propinitas.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PropinitasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipCalculatorApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// 🔤 Fuente Pokémon
val PokemonFont = FontFamily(
    Font(R.font.pokemon)
)

@Composable
fun TipCalculatorApp(modifier: Modifier = Modifier) {

    var amountInput by remember { mutableStateOf("") }
    var tipPercentage by remember { mutableStateOf(15) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tip = amount * tipPercentage / 100

    val infiniteTransition = rememberInfiniteTransition()

    val rotation by infiniteTransition.animateFloat(
        0f, 360f,
        infiniteRepeatable(tween(6000, easing = LinearEasing))
    )

    val gold = Color(0xFFD4AF37)
    val black = Color(0xFF0A0A0A)
    val darkGray = Color(0xFF1C1C1C)

    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            black,
                            darkGray,
                            gold.copy(alpha = 0.2f)
                        )
                    )
                )
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = black
                ),
                elevation = CardDefaults.cardElevation(15.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(28.dp)
                        .width(300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(18.dp)
                ) {

                    // 🖼️ Imagen
                    Image(
                        painter = painterResource(id = R.drawable.arceus),
                        contentDescription = "ARCELOGO",
                        modifier = Modifier
                            .size(110.dp)
                            .graphicsLayer(rotationZ = rotation)
                    )

                    Text(
                        text = "POKE TIPS",
                        fontFamily = PokemonFont,
                        style = MaterialTheme.typography.headlineMedium,
                        color = gold
                    )

                    Text(
                        text = "CÁLCULO DE GASTO",
                        color = Color.LightGray,
                        fontFamily = PokemonFont
                    )

                    TextField(
                        value = amountInput,
                        onValueChange = { amountInput = it },
                        label = {
                            Text(
                                "Cantidad de pokemonedas",
                                fontFamily = PokemonFont,
                                color = gold
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = darkGray,
                            unfocusedContainerColor = darkGray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )

                    Text(
                        text = "PORCENTAJE: $tipPercentage%",
                        color = gold,
                        fontFamily = PokemonFont
                    )

                    Slider(
                        value = tipPercentage.toFloat(),
                        onValueChange = { tipPercentage = it.toInt() },
                        valueRange = 0f..30f,
                        colors = SliderDefaults.colors(
                            thumbColor = gold,
                            activeTrackColor = gold
                        )
                    )

                    Text(
                        text = "TOTAL DE POKEPROPINA: $${"%.2f".format(tip)}",
                        color = Color.White,
                        fontFamily = PokemonFont
                    )
                }
            }
        }
    }
}