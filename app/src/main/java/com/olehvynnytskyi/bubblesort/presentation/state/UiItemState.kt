package com.olehvynnytskyi.bubblesort.presentation.state

import androidx.compose.ui.graphics.Color

data class UiItemState(
    val id: Int,
    val isCurrentlyCompared: Boolean,
    val value: Int,
    val color: Color,
)