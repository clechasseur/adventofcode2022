package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.util.Pt
import io.github.clechasseur.adventofcode.y2022.data.Day14Data
import kotlin.math.sign

object Day14 {
    private val input = Day14Data.input

    fun part1(): Int {
        val cave = input.toCave()
        val minX = cave.solid.minOf { it.x }
        val maxX = cave.solid.maxOf { it.x }
        val minY = 0
        val maxY = cave.abyss
        (minY..maxY).forEach { y ->
            println((minX..maxX).joinToString("") { x -> when {
                Pt(x, y) == Pt(500, 0) -> "+"
                cave.solid.contains(Pt(x, y)) -> "#"
                cave.sand.contains(Pt(x, y)) -> "o"
                else -> "."
            } })
        }
        return -1
    }

    private data class Cave(val solid: Set<Pt>, val sand: Set<Pt> = emptySet()) {
        val abyss: Int
            get() = solid.maxOf { it.y } + 1
    }

    private fun String.toCave(): Cave = Cave(lines().flatMap { line ->
        line.split(" -> ").map { pt ->
            val (x, y) = pt.split(",")
            Pt(x.toInt(), y.toInt())
        }.zipWithNext().flatMap { it.asLine() }
    }.toSet())

    private fun Pair<Pt, Pt>.asLine(): Sequence<Pt> {
        val displacement = Pt((second.x - first.x).sign, (second.y - first.y).sign)
        return generateSequence(first) { it + displacement }.takeWhile { (it - displacement) != second }
    }
}
