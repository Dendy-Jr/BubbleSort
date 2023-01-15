package com.olehvynnytskyi.bubblesort.presentation.screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.olehvynnytskyi.bubblesort.R
import com.olehvynnytskyi.bubblesort.presentation.viewmodel.SortViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BubbleSortScreen(
    modifier: Modifier = Modifier,
    viewModel: SortViewModel,
) {
    val context = LocalContext.current
    val btnIsEnabled = viewModel.btnIsEnabled.collectAsState().value
    val textBtnColor = if (btnIsEnabled) Color.Black else Color.LightGray

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            OutlinedButton(
                onClick = {
                    viewModel.getBubbleList()
                },
                enabled = btnIsEnabled,

                ) {
                Text(
                    text = stringResource(R.string.generate),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = textBtnColor,
                )
            }

            OutlinedButton(
                onClick = {
                    viewModel.startSorting()
                },
                enabled = btnIsEnabled,
            ) {
                Text(
                    text = context.getString(R.string.sort_list),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = textBtnColor,
                )
            }
        }

        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            items(
                items = viewModel.listState,
                key = {
                    it.id
                },
            ) {
                val borderStroke = if (it.isCurrentlyCompared) {
                    BorderStroke(width = 3.dp, Color.White)
                } else {
                    BorderStroke(width = 0.dp, Color.Transparent)
                }

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(it.color, RoundedCornerShape(10.dp))
                        .border(borderStroke, RoundedCornerShape(10.dp))
                        .animateItemPlacement(
                            tween(300)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "${it.value}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                }
            }
        }
    }
}