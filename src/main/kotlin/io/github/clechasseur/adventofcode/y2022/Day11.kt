package io.github.clechasseur.adventofcode.y2022

object Day11 {
    private val input = listOf(
        Monkey(
            id = 0,
            items = listOf(63L, 84L, 80L, 83L, 84L, 53L, 88L, 72L),
            op = { it * 11L },
            test = { if (it % 13L == 0L) 4 else 7 }
        ),
        Monkey(
            id = 1,
            items = listOf(67L, 56L, 92L, 88L, 84L),
            op = { it + 4L },
            test = { if (it % 11L == 0L) 5 else 3 }
        ),
        Monkey(
            id = 2,
            items = listOf(52L),
            op = { it * it },
            test = { if (it % 2L == 0L) 3 else 1 }
        ),
        Monkey(
            id = 3,
            items = listOf(59L, 53L, 60L, 92L, 69L, 72L),
            op = { it + 2L },
            test = { if (it % 5L == 0L) 5 else 6 }
        ),
        Monkey(
            id = 4,
            items = listOf(61L, 52L, 55L, 61L),
            op = { it + 3L },
            test = { if (it % 7L == 0L) 7 else 2 }
        ),
        Monkey(
            id = 5,
            items = listOf(79L, 53L),
            op = { it + 1L },
            test = { if (it % 3L == 0L) 0 else 6 }
        ),
        Monkey(
            id = 6,
            items = listOf(59L, 86L, 67L, 95L, 92L, 77L, 91L),
            op = { it + 5L },
            test = { if (it % 19L == 0L) 4 else 0 }
        ),
        Monkey(
            id = 7,
            items = listOf(58L, 83L, 89L),
            op = { it * 19L },
            test = { if (it % 17L == 0L) 2 else 1 }
        )
    )
    private const val clonazepam = 9_699_690L // product of all X in "divisible by X"

    fun part1(): Long = generateSequence(input) {
        it.playOneRound(boredMonkeys = true)
    }.drop(20).first().sortedByDescending {
        it.business
    }.take(2).fold(1L) { acc, monkey ->
        acc * monkey.business
    }

    fun part2(): Long = generateSequence(input) {
        it.playOneRound(boredMonkeys = false)
    }.drop(10_000).first().sortedByDescending {
        it.business
    }.take(2).fold(1L) { acc, monkey ->
        acc * monkey.business
    }

    private data class Monkey(
        val id: Int,
        val items: List<Long>,
        val op: (Long) -> Long,
        val test: (Long) -> Int,
        val business: Long = 0L
    ) {
        fun proceed(bored: Boolean): Pair<Monkey, Map<Int, List<Long>>> = copy(
            items = listOf(),
            business = business + items.size
        ) to items.map { worry ->
            val newWorry = (op(worry) / if (bored) 3L else 1L) % clonazepam
            test(newWorry) to newWorry
        }.fold(mutableMapOf()) { m, (destMonkey, item) ->
            m[destMonkey] = (m[destMonkey] ?: listOf()) + item
            m
        }

        fun add(newItems: List<Long>): Monkey = copy(items = items + newItems)
    }

    private fun List<Monkey>.playOneTurn(boredMonkeys: Boolean): List<Monkey> {
        val (newLast, result) = first().proceed(boredMonkeys)
        return drop(1).map { it.add(result[it.id] ?: listOf()) } + newLast
    }

    private fun List<Monkey>.playOneRound(boredMonkeys: Boolean): List<Monkey> = generateSequence(this) {
        it.playOneTurn(boredMonkeys)
    }.drop(size).first()
}
