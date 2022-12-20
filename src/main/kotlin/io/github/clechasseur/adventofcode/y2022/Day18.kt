package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.util.Pt3D
import io.github.clechasseur.adventofcode.y2022.data.Day18Data

object Day18 {
    private val input = Day18Data.input

    private val displacements = listOf(
        Pt3D(-1, 0, 0),
        Pt3D(1, 0, 0),
        Pt3D(0, -1, 0),
        Pt3D(0, 1, 0),
        Pt3D(0, 0, -1),
        Pt3D(0, 0, 1)
    )

    fun part1(): Int {
        val cubes = input.lines().map { it.toPt3D() }.toSet()
        return cubes.sumOf { cubes.exposedSides(it) }
    }

    fun part2(): Int {
        val cubes = input.lines().map { it.toPt3D() }.toSet()
        val trappedAirPockets = cubes.trappedAirPockets()
        return cubes.sumOf { cubes.exposedSides(it) } - trappedAirPockets * 6
    }

    private fun Set<Pt3D>.exposedSides(pt: Pt3D): Int = displacements.map { pt + it }.count { adjacentPt ->
        !contains(adjacentPt)
    }

    private fun Set<Pt3D>.trappedAirPockets(): Int = asSequence().flatMap { pt ->
        displacements.map { pt + it }
    }.distinct().count { pt ->
        exposedSides(pt) == 0 && !contains(pt)
    }

    private fun String.toPt3D(): Pt3D {
        val (x, y, z) = split(',')
        return Pt3D(x.toInt(), y.toInt(), z.toInt())
    }
}
