package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.y2022.data.Day21Data

object Day21 {
    private val input = Day21Data.input

    fun part1(): Long {
        val monkeys = input.toMonkeys()
        return monkeys["root"]!!.compute(monkeys)
    }

    private val mathRegex = """(\w+) ([+*/-]) (\w+)""".toRegex()
    private val monkeyRegex = "(\\w+): (\\d+|${mathRegex.pattern})".toRegex()

    private val ops = mapOf<String, (Long, Long) -> Long>(
        "+" to { a, b -> a + b },
        "-" to { a, b -> a - b },
        "*" to { a, b -> a * b },
        "/" to { a, b -> a / b },
    )

    private abstract class Monkey {
        abstract fun compute(monkeys: Map<String, Monkey>): Long
    }

    private class Yeller(val number: Long) : Monkey() {
        override fun compute(monkeys: Map<String, Monkey>): Long = number
    }

    private class MathHead(val monkey1: String, val monkey2: String, val computer: (Long, Long) -> Long) : Monkey() {
        override fun compute(monkeys: Map<String, Monkey>): Long =
            computer(monkeys[monkey1]!!.compute(monkeys), monkeys[monkey2]!!.compute(monkeys))
    }

    private fun String.toMonkeys(): Map<String, Monkey> = lines().associate { line ->
        val match = monkeyRegex.matchEntire(line) ?: error("Wrong monkey line: $line")
        val (name, yell) = match.destructured
        val number = yell.toLongOrNull()
        if (number != null) {
            name to Yeller(number)
        } else {
            val mathMatch = mathRegex.matchEntire(yell) ?: error("Wrong math yell: $yell")
            val (monkey1, op, monkey2) = mathMatch.destructured
            name to MathHead(monkey1, monkey2, ops[op]!!)
        }
    }
}
