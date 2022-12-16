package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.y2022.data.Day16Data

object Day16 {
    private val input = Day16Data.input

    private val valveRegex = """^Valve ([A-Z]{2}) has flow rate=(\d+); tunnels? leads? to valves? ([A-Z]{2}(?:, [A-Z]{2})*)$""".toRegex()

    fun part1(): Int = simulate(State(
        valves = input.lines().map { it.toValve() }.map { it.id to it }.toMap(),
        pos = "AA",
        open = emptySet(),
        minutesRemaining = 30,
        releasedPressure = 0
    )).maxOf { it.releasedPressure }

    private data class Valve(val id: String, val flowRate: Int, val tunnels: List<String>)

    private data class State(
        val valves: Map<String, Valve>,
        val pos: String,
        val open: Set<String>,
        val minutesRemaining: Int,
        val releasedPressure: Int
    ) {
        val workingValves: Map<String, Valve>
            get() = valves.filterValues { it.flowRate > 0 }

        fun nextMoves(): List<State> {
            if (minutesRemaining == 0) {
                return emptyList()
            }

            val nextReleasedPressure = releasedPressure + open.sumOf { valves[it]!!.flowRate }
            val nextMinutesRemaining = minutesRemaining - 1
            if (open == workingValves.keys) {
                return listOf(copy(
                    releasedPressure = nextReleasedPressure,
                    minutesRemaining = nextMinutesRemaining
                ))
            }

            val curValve = valves[pos]!!
            val moves = curValve.tunnels.map { nextValve ->
                copy(
                    pos = nextValve,
                    minutesRemaining = nextMinutesRemaining,
                    releasedPressure = nextReleasedPressure
                )
            }.toMutableList()
            if (curValve.id !in open && curValve.flowRate > 0) {
                moves += copy(
                    open = open + curValve.id,
                    minutesRemaining = nextMinutesRemaining,
                    releasedPressure = nextReleasedPressure
                )
            }

            return moves
        }
    }

    private data class StateId(val pos: String, val open: Set<String>) {
        constructor(state: State) : this(state.pos, state.open)
    }

    private fun simulate(initialState: State): Set<State> {
        var states = setOf(initialState)
        while (true) {
            val nextStates = states.flatMap { it.nextMoves() }.toSet()
            if (nextStates.isEmpty()) {
                return states
            }
            val nextIds = nextStates.map { StateId(it) }.toSet()
            states = nextIds.map { id ->
                nextStates.filter { StateId(it) == id }.maxBy { it.releasedPressure }
            }.toSet()
        }
    }

    private fun String.toValve(): Valve {
        val match = valveRegex.matchEntire(this) ?: error("Wrong valve spec: $this")
        val (id, flowRate, tunnels) = match.destructured
        return Valve(id, flowRate.toInt(), tunnels.split(", "))
    }
}
