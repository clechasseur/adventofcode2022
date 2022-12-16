package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.dij.Dijkstra
import io.github.clechasseur.adventofcode.dij.Graph
import io.github.clechasseur.adventofcode.y2022.data.Day16Data

object Day16 {
    private val input = Day16Data.input

    private val valveRegex = """^Valve ([A-Z]{2}) has flow rate=(\d+); tunnels? leads? to valves? ([A-Z]{2}(?:, [A-Z]{2})*)$""".toRegex()

    fun part1(): Int = simulate(State(
        network = Network(input.lines().map { it.toValve() }),
        pos = "AA",
        open = emptySet(),
        minutesRemaining = 30,
        releasedPressure = 0
    )).maxOf { it.releasedPressure }

    private data class Valve(val id: String, val flowRate: Int, val tunnels: List<String>)

    private data class Network(val valves: Map<String, Valve>) : Graph<String> {
        constructor(valves: List<Valve>) : this(valves.associateBy { it.id })

        val workingValves: Map<String, Valve>
            get() = valves.filterValues { it.flowRate > 0 }

        override fun allPassable(): List<String> = valves.keys.toList()
        override fun neighbours(node: String): List<String> = valves[node]!!.tunnels
        override fun dist(a: String, b: String): Long = 1L
    }

    private data class State(
        val network: Network,
        val pos: String,
        val open: Set<String>,
        val minutesRemaining: Int,
        val releasedPressure: Int
    ) {
        fun nextMoves(): List<State> {
            if (minutesRemaining == 0) {
                return emptyList()
            }

            val stillClosed = network.workingValves - open
            if (stillClosed.isEmpty()) {
                return listOf(copy(
                    pos = "AA",
                    minutesRemaining = 0,
                    releasedPressure = releasedPressure + network.workingValves.values.sumOf { valve ->
                        valve.flowRate * minutesRemaining
                    }
                ))
            }

            val (dist, _) = Dijkstra.build(network, pos)
            val moves = stillClosed.values.mapNotNull { valve ->
                val valveDist = (dist[valve.id]!! + 1).toInt()
                if (valveDist > minutesRemaining) null else {
                    copy(
                        pos = valve.id,
                        open = open + valve.id,
                        minutesRemaining = minutesRemaining - valveDist,
                        releasedPressure = releasedPressure + open.sumOf { openValve ->
                            network.valves[openValve]!!.flowRate * valveDist
                        }
                    )
                }
            }
            if (moves.isEmpty()) {
                return listOf(copy(
                    pos = "AA",
                    minutesRemaining = 0,
                    releasedPressure = releasedPressure + open.sumOf { openValve ->
                        network.valves[openValve]!!.flowRate * minutesRemaining
                    }
                ))
            }
            return moves
        }
    }

    private fun simulate(initialState: State): Set<State> {
        var states = setOf(initialState)
        val finalStates = mutableSetOf<State>()
        while (states.isNotEmpty()) {
            val (nextStates, newFinalStates) = states.flatMap {
                it.nextMoves()
            }.partition {
                it.minutesRemaining > 0
            }
            finalStates += newFinalStates
            states = nextStates.toSet()
        }
        return finalStates
    }

    private fun String.toValve(): Valve {
        val match = valveRegex.matchEntire(this) ?: error("Wrong valve spec: $this")
        val (id, flowRate, tunnels) = match.destructured
        return Valve(id, flowRate.toInt(), tunnels.split(", "))
    }
}
