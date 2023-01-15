package com.olehvynnytskyi.bubblesort.domain.repository

interface BubbleSortRepository {

    fun generateBubbleList(): List<Int>

    fun clearList()
}