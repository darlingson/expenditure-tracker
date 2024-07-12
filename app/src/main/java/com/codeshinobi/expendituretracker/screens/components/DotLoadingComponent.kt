package com.codeshinobi.expendituretracker.screens.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DotLoadingAnimation(
    dotSize: Dp = 12.dp,
    dotColor: Color = Color.Black,
    delayUnit: Int = 300
) {
    val dots = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) }
    )

    dots.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = delayUnit * 5
                        0.0f at 0 with LinearEasing
                        1.0f at delayUnit with LinearEasing
                        0.0f at delayUnit * 2 with LinearEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    Row {
        dots.forEachIndexed { index, animatable ->
            if (index != 0) {
                Spacer(modifier = Modifier.size(dotSize / 2))
            }
            Canvas(modifier = Modifier.size(dotSize)) {
                drawCircle(
                    color = dotColor.copy(alpha = animatable.value),
                    radius = dotSize.toPx() / 2
                )
            }
        }
    }
}
