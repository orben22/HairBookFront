package com.example.hairbookfront.ui.common

import android.graphics.Path
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.hairbookfront.R
import kotlin.math.roundToInt


@Composable
fun RatingComponent(rating: Float) {
    val roundedRating = (rating * 2).roundToInt() / 2f
    Row {
        for (i in 1..5) {
            when {
                roundedRating >= i -> {
                    Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                }

                roundedRating > i - 1 -> {
                    val halfStarResource = if (isSystemInDarkTheme()) {
                        R.drawable.baseline_star_half_24_dark
                    } else {
                        R.drawable.baseline_star_half_24
                    }
                    Image(painterResource(id = halfStarResource), contentDescription = null)
                }

                else -> {
                    val emptyStarResource = if (isSystemInDarkTheme()) {
                        R.drawable.baseline_star_border_24_dark
                    } else {
                        R.drawable.baseline_star_outline_24
                    }
                    Image(painterResource(id = emptyStarResource), contentDescription = null)
                }
            }
        }
    }
}
