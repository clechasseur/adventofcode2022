package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.util.Pt
import io.github.clechasseur.adventofcode.y2022.data.Day14Data
import kotlin.math.sign

object Day14 {
    private val input = Day14Data.input

    fun part1(): Int = generateSequence(input.toCave()) {
        it.dropOneSand(Pt(500, 0))
    }.last().sand.size

    private data class Cave(val solid: Set<Pt>, val sand: Set<Pt> = emptySet()) {
        val obstacles: Set<Pt> = solid + sand
        val abyss: Int = solid.maxOf { it.y } + 1

        fun dropOneSand(from: Pt): Cave? {
            var pt = from
            while (pt.y < abyss) {
                var nextPt = pt + Pt(0, 1)
                if (obstacles.contains(nextPt)) {
                    nextPt = pt + Pt(-1, 1)
                    if (obstacles.contains(nextPt)) {
                        nextPt = pt + Pt(1, 1)
                        if (obstacles.contains(nextPt)) {
                            return Cave(solid, sand + pt)
                        }
                    }
                }
                pt = nextPt
            }
            return null
        }
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
