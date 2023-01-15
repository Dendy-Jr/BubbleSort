package com.olehvynnytskyi.bubblesort.data

import com.olehvynnytskyi.bubblesort.domain.repository.BubbleSortRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class BubbleSortRepositoryImpl @Inject constructor() : BubbleSortRepository {

    private val list = mutableListOf<Int>()

    override fun generateBubbleList(): List<Int> {
        return list.apply {
            val random = Random()
            for (i in 0 until 10) {
                add(random.nextInt(500))
            }
        }
    }

    override fun clearList() {
        list.clear()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface BubbleSortRepositoryModule {

    @Singleton
    @Binds
    fun binds(impl: BubbleSortRepositoryImpl): BubbleSortRepository
}