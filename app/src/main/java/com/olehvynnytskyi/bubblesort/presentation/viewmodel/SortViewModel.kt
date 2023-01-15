package com.olehvynnytskyi.bubblesort.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olehvynnytskyi.bubblesort.domain.repository.BubbleSortRepository
import com.olehvynnytskyi.bubblesort.domain.use_case.BubbleSortUseCase
import com.olehvynnytskyi.bubblesort.presentation.state.UiItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SortViewModel @Inject constructor(
    private val bubbleSortUseCase: BubbleSortUseCase,
    private val repository: BubbleSortRepository,
) : ViewModel() {

    private val _btnIsEnabled = MutableStateFlow(true)
    val btnIsEnabled = _btnIsEnabled.asStateFlow()

    var listState = mutableStateListOf<UiItemState>()
        private set

    init {
        getBubbleList()
    }

    fun startSorting() {
        viewModelScope.launch {
            bubbleSortUseCase(listState.map { listUiItem ->
                listUiItem.value
            }.toMutableList()).collect { swapInfo ->
                val currentItemIndex = swapInfo.currentItem
                listState[currentItemIndex] =
                    listState[currentItemIndex].copy(isCurrentlyCompared = true)
                listState[currentItemIndex + 1] =
                    listState[currentItemIndex + 1].copy(isCurrentlyCompared = true)

                if (swapInfo.shouldSwap) {
                    val firstItem =
                        listState[currentItemIndex].copy(isCurrentlyCompared = false)
                    listState[currentItemIndex] =
                        listState[currentItemIndex + 1].copy(isCurrentlyCompared = false)
                    listState[currentItemIndex + 1] = firstItem
                }
                if (swapInfo.hadNoEffect) {
                    listState[currentItemIndex] =
                        listState[currentItemIndex].copy(isCurrentlyCompared = false)
                    listState[currentItemIndex + 1] =
                        listState[currentItemIndex + 1].copy(isCurrentlyCompared = false)
                }
                checkListIsSorted(list = listState.toList().map { it.value }, scope = this@launch)
            }
        }
    }

    fun getBubbleList() {
        clearList()
        val list = repository.generateBubbleList()
        for (i in list.indices) {
            val random = Random()
            listState.add(
                UiItemState(
                    id = i,
                    isCurrentlyCompared = false,
                    value = list[i],
                    color = Color(
                        255,
                        random.nextInt(256),
                        random.nextInt(256),
                        255
                    )
                )
            )
        }
    }

    private fun clearList() {
        repository.clearList()
        listState.clear()
    }

    private fun checkListIsSorted(list: List<Int>, scope: CoroutineScope) {
        val isSorted = listState.toList().map { it.value } == list.sorted()
        if (isSorted) scope.cancel()
        _btnIsEnabled.value = listState.toList().map { it.value } == list.sorted()
    }
}