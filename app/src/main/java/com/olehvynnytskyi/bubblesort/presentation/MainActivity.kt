package com.olehvynnytskyi.bubblesort.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.olehvynnytskyi.bubblesort.presentation.screen.BubbleSortScreen
import com.olehvynnytskyi.bubblesort.presentation.viewmodel.SortViewModel
import com.olehvynnytskyi.bubblesort.ui.theme.BubbleSortTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SortViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BubbleSortTheme {
                BubbleSortScreen(viewModel = viewModel)
            }
        }
    }
}