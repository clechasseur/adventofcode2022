package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.dij.Dijkstra
import io.github.clechasseur.adventofcode.dij.Graph
import io.github.clechasseur.adventofcode.util.Direction
import io.github.clechasseur.adventofcode.util.Pt
import io.github.clechasseur.adventofcode.y2022.data.Day12Data

object Day12 {
    private val input = Day12Data.input

    fun part1(): Int {
        val heightmap = Heightmap(input)
        val (dist, _) = Dijkstra.build(heightmap, heightmap.startPos)
        return dist[heightmap.endPos]!!.toInt()
    }

    private class Heightmap(asString: String) : Graph<Pt> {
        val pts: Map<Pt, Char> = asString.lineSequence().flatMapIndexed { y, line ->
            line.mapIndexed { x, elevation -> Pt(x, y) to elevation }
        }.toMap()

        val startPos: Pt
            get() = pts.entries.single { it.value == 'S' }.key
        val endPos: Pt
            get() = pts.entries.single { it.value == 'E' }.key

        fun elevation(pt: Pt): Char = when (val e = pts[pt]) {
            'S' -> 'a'
            'E', null -> 'z'
            else -> e
        }

        override fun allPassable(): List<Pt> = pts.keys.toList()
        override fun neighbours(node: Pt): List<Pt> = Direction.displacements.map {
            node + it
        }.filter {
            elevation(it) - elevation(node) <= 1
        }
        override fun dist(a: Pt, b: Pt): Long = 1L
    }
}
