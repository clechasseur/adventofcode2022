package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.util.Direction
import io.github.clechasseur.adventofcode.util.Pt
import io.github.clechasseur.adventofcode.util.move
import io.github.clechasseur.adventofcode.y2022.data.Day22Data

object Day22 {
    private val mapOfBoard = Day22Data.mapOfBoard
    private const val instructions = Day22Data.instructions

    fun part1(): Int {
        val board = Board(mapOfBoard)
        val bot = Bot(Pt.ZERO, Direction.RIGHT).advance(board)
        val finalBot = bot.apply(instructions.toInstructions(), board)
        return finalBot.password
    }

    private val TURN_INSTRUCTIONS = setOf('L', 'R')

    private class Board(mapOfBoard: String) {
        private val tiles = mapOfBoard.lineSequence().flatMapIndexed { y, line ->
            line.mapIndexed { x, c -> Pt(x, y) to c }
        }.toMap().filterValues { it == '.' || it == '#' }

        private val height = mapOfBoard.lines().size
        private val width = mapOfBoard.lines().maxOf { it.length }

        operator fun get(pt: Pt): Char = tiles[pt] ?: ' '

        fun move(pt: Pt, direction: Direction): Pt? {
            val n = pt.move(direction).constraint()
            return when (val c = this[n]) {
                '.' -> n
                '#' -> null
                ' ' -> move(n, direction)
                else -> error("Wrong tile: $c")
            }
        }

        private fun Pt.constraint(): Pt = Pt((x + width) % width, (y + height) % height)
    }

    private data class Bot(val pos: Pt, val facing: Direction) {
        val password: Int
            get() = 1000 * (pos.y + 1) + 4 * (pos.x + 1) + when (facing) {
                Direction.RIGHT -> 0
                Direction.DOWN -> 1
                Direction.LEFT -> 2
                Direction.UP -> 3
            }

        fun advance(board: Board): Bot = Bot(board.move(pos, facing) ?: pos, facing)
        fun turnLeft(): Bot = Bot(pos, facing.left)
        fun turnRight(): Bot = Bot(pos, facing.right)

        fun apply(instruction: String, board: Board): Bot = when (instruction) {
            "L" -> turnLeft()
            "R" -> turnRight()
            else -> generateSequence(this) { it.advance(board) }.elementAt(instruction.toInt())
        }
        fun apply(instructions: List<String>, board: Board) = instructions.fold(this) { bot, instruction ->
            bot.apply(instruction, board)
        }
    }

    private fun String.toInstructions(): List<String> {
        val instructionList = mutableListOf<String>()
        var i = 0
        while (i in indices) {
            if (this[i] in TURN_INSTRUCTIONS) {
                instructionList.add(this[i].toString())
                i++
            } else {
                val match = """(\d+)""".toRegex().find(this, i) ?: error("Wrong instruction sequence: $this")
                instructionList.add(match.groupValues[1])
                i += match.value.length
            }
        }
        return instructionList
    }
}
