package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.util.Direction
import io.github.clechasseur.adventofcode.util.Pt
import io.github.clechasseur.adventofcode.y2022.data.Day17Data

object Day17 {
    private val shapes = Day17Data.shapes.map { it.toShape() }
    private const val jets = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>" //Day17Data.jets

    fun part1(): Int = caveSequence().take(2022).last().height

    fun part2(): Long {
        val loopSize = shapes.size * jets.length
        val numRocks = 1_000_000_000_000L
        val extra = (numRocks % loopSize.toLong()).toInt()
        val oneLoopHeight = caveSequence().take(loopSize).last().height
        val twoLoopHeight = caveSequence().take(loopSize * 2).last().height
        val middleHeight = (twoLoopHeight - oneLoopHeight).toLong() * (numRocks / loopSize.toLong() - 2L)
        return caveSequence().take(loopSize * 2 + extra).last().height.toLong() + middleHeight
    }

    private class Shape(val pts: Set<Pt>) {
        val height: Int
            get() = pts.maxOf { it.y } + 1

        fun move(displacement: Pt): Shape = Shape(pts.map { it + displacement }.toSet())
    }

    private class Cave(val rocks: Set<Pt> = emptySet()) {
        val height: Int
            get() = rocks.maxOfOrNull { -it.y }?.plus(1) ?: 0

        fun blocked(pt: Pt): Boolean = rocks.contains(pt) || pt.x < 0 || pt.x >= 7 || pt.y > 0
        fun fits(shape: Shape): Boolean = shape.pts.none { blocked(it) }
        fun land(shape: Shape): Cave = Cave(rocks + shape.pts)

        override fun toString(): String = (rocks.minOf { it.y }..rocks.maxOf { it.y }).joinToString("\n") { y ->
            val middle = (0..6).joinToString("") { x ->
                if (rocks.contains(Pt(x, y))) "#" else "."
            }
            "|$middle|"
        }
    }

    private fun shapeSequence(): Sequence<Shape> = generateSequence(0) { it + 1 }.map { i ->
        shapes[i % shapes.size]
    }

    private fun jetSequence(): Sequence<Direction> = generateSequence(0) { it + 1 }.map { i ->
        when (jets[i % jets.length]) {
            '<' -> Direction.LEFT
            '>' -> Direction.RIGHT
            else -> error("Wrong jet: ${jets[i % jets.length]}")
        }
    }

    private fun caveSequence(): Sequence<Cave> = sequence {
        var cave = Cave()
        val shapeIt = shapeSequence().iterator()
        val jetIt = jetSequence().iterator()
        while (true) {
            var shape = shapeIt.next()
            shape = shape.move(Pt(2, -(cave.height + 3 + shape.height - 1)))
            while (true) {
                val jet = jetIt.next()
                val pushedShape = shape.move(jet.displacement)
                if (cave.fits(pushedShape)) {
                    shape = pushedShape
                }

                val fallenShape = shape.move(Direction.DOWN.displacement)
                if (cave.fits(fallenShape)) {
                    shape = fallenShape
                } else {
                    cave = cave.land(shape)
                    yield(cave)
                    break
                }
            }
        }
    }

    private fun String.toShape(): Shape = Shape(lines().withIndex().flatMap { (y, line) ->
        line.withIndex().mapNotNull { (x, c) -> if (c == '#') Pt(x, y) else null }
    }.toSet())
}
