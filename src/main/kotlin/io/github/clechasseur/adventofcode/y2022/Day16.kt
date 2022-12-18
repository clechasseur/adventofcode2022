package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.dij.Dijkstra
import io.github.clechasseur.adventofcode.dij.Graph
import io.github.clechasseur.adventofcode.y2022.data.Day16Data
import kotlin.math.max

object Day16 {
    private val input = Day16Data.input

    private val valveRegex = """^Valve ([A-Z]{2}) has flow rate=(\d+); tunnels? leads? to valves? ([A-Z]{2}(?:, [A-Z]{2})*)$""".toRegex()

    fun part1(): Int = simulate(Network(input.lines().map { it.toValve() }), 0)

    fun part2(): Int = simulate(Network(input.lines().map { it.toValve() }), 1)

    private data class Valve(val id: String, val flowRate: Int, val tunnels: List<String>)

    private data class Network(val valves: Map<String, Valve>) : Graph<String> {
        constructor(valves: List<Valve>) : this(valves.associateBy { it.id })

        val workingValves: Map<String, Valve>
            get() = valves.filterValues { it.flowRate > 0 }
        val dij = valves.keys.associateWith { Dijkstra.build(this, it) }

        override fun allPassable(): List<String> = valves.keys.toList()
        override fun neighbours(node: String): List<String> = valves[node]!!.tunnels
        override fun dist(a: String, b: String): Long = 1L
    }

    private data class Moving(val destValve: String, val arriveAt: Int)

    private data class State(
        val open: Set<String>,
        val minutesRemaining: Int,
        val releasedPressure: Int,
        val still: List<String>,
        val moving: List<Moving>
    ) {
        constructor(numElephants: Int) : this(
            open = emptySet(),
            minutesRemaining = 30 - 4 * numElephants,
            releasedPressure = 0,
            still = List(numElephants + 1) { "AA" },
            moving = emptyList()
        )

        fun nextMoves(network: Network): Sequence<State> {
            if (minutesRemaining == 0 || open == network.workingValves.keys) {
                return sequenceOf(copy(
                    minutesRemaining = 0,
                    releasedPressure = releasedPressure + pressureForMinutes(network, open, minutesRemaining),
                    still = List(still.size) { "AA" },
                    moving = emptyList()
                ))
            }

            if (still.isEmpty()) {
                val nextMinutes = moving.maxOf { it.arriveAt }
                val stopping = moving.filter { it.arriveAt == nextMinutes }.map { it.destValve }
                return sequenceOf(copy(
                    open = open + stopping,
                    minutesRemaining = nextMinutes,
                    releasedPressure = releasedPressure + pressureForMinutes(
                        network,
                        open,
                        minutesRemaining - nextMinutes
                    ),
                    still = still + stopping,
                    moving = moving.filterNot { it.arriveAt == nextMinutes }
                ))
            }

            val nextToMove = still.first()
            return (network.workingValves.keys - open).asSequence().map { destValve ->
                Moving(destValve, minutesRemaining - (network.dij[nextToMove]!!.dist[destValve]!!.toInt() + 1))
            }.filter { move ->
                move.arriveAt >= 0
            }.map { move ->
                copy(still = still - nextToMove, moving = moving + move)
            } + sequenceOf(copy(
                still = still - nextToMove,
                moving = moving + Moving(nextToMove, 0)
            ))
        }

        fun maxPotentialReleasedPressure(network: Network): Int = releasedPressure + pressureForMinutes(
            network,
            network.workingValves.keys,
            minutesRemaining
        )

        private fun pressureForMinutes(network: Network, openValves: Collection<String>, minutes: Int): Int {
            return openValves.sumOf { network.valves[it]!!.flowRate * minutes }
        }
    }

    private fun simulate(network: Network, numElephants: Int): Int {
        var states = setOf(State(numElephants))
        var maxReleasedPressure = 0
        while (states.isNotEmpty()) {
            val (nextStates, finalStates) = states.flatMap {
                it.nextMoves(network)
            }.partition {
                it.minutesRemaining > 0
            }
            if (finalStates.isNotEmpty()) {
                maxReleasedPressure = max(maxReleasedPressure, finalStates.maxOf { it.releasedPressure })
            }
            states = nextStates.filter { state ->
                state.maxPotentialReleasedPressure(network) > maxReleasedPressure
            }.toSet()
        }
        return maxReleasedPressure
    }

    private fun String.toValve(): Valve {
        val match = valveRegex.matchEntire(this) ?: error("Wrong valve spec: $this")
        val (id, flowRate, tunnels) = match.destructured
        return Valve(id, flowRate.toInt(), tunnels.split(", "))
    }
}
