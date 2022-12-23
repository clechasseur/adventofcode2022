package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.y2022.data.Day20Data

object Day20 {
    private val input = Day20Data.input

    private const val decryptionKey = 811589153L

    fun part1(): Long = findGrove(input.lines().map { it.toLong() }, 1)

    fun part2(): Long = findGrove(input.lines().map { it.toLong() * decryptionKey }, 10)

    private fun findGrove(file: List<Long>, mixTimes: Int): Long {
        val indexedFile = file.withIndex().toMutableList()
        (0 until mixTimes).forEach { _ ->
            file.indices.forEach { i ->
                val mixedI = indexedFile.indexOfFirst { it.index == i }
                val value = indexedFile[mixedI].value
                val newMixedI = if (value >= 0) {
                    ((mixedI.toLong() + value + 1L) % indexedFile.size.toLong()).toInt()
                } else {
                    indexedFile.size - ((indexedFile.size.toLong() - mixedI - value) % indexedFile.size.toLong()).toInt()
                }
                if (newMixedI <= mixedI) {
                    indexedFile.removeAt(mixedI)
                    indexedFile.add(newMixedI, IndexedValue(i, value))
                } else {
                    indexedFile.add(newMixedI, IndexedValue(i, value))
                    indexedFile.removeAt(mixedI)
                }
            }
        }

        indexedFile.forEach { println("${it.index}:${it.value}") }

        val i0 = indexedFile.indexOfFirst { it.value == 0L }
        val i1000 = (i0 + 1000) % indexedFile.size
        val i2000 = (i0 + 2000) % indexedFile.size
        val i3000 = (i0 + 3000) % indexedFile.size
        return indexedFile[i1000].value + indexedFile[i2000].value + indexedFile[i3000].value
    }
}
