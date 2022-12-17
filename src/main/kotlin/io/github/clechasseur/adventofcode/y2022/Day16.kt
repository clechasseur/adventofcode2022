package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.dij.Dijkstra
import io.github.clechasseur.adventofcode.dij.Graph
import io.github.clechasseur.adventofcode.y2022.data.Day16Data

object Day16 {
    private val input = Day16Data.input

    private val valveRegex = """^Valve ([A-Z]{2}) has flow rate=(\d+); tunnels? leads? to valves? ([A-Z]{2}(?:, [A-Z]{2})*)$""".toRegex()

    fun part1(): Int = simulate(
        Network(input.lines().map { it.toValve() }),
        State(pos = listOf("AA"), open = emptySet(), minutesRemaining = 30, releasedPressure = 0)
    ).maxOf { it.releasedPressure }

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
        val pos: List<String>,
        val open: Set<String>,
        val minutesRemaining: Int,
        val releasedPressure: Int
    ) {
        fun nextMoves(network: Network): List<State> {
            if (minutesRemaining == 0) {
                return emptyList()
            }

            val stillClosed = network.workingValves.keys - open
            val dij = pos.map { it to Dijkstra.build(network, it) }
            val moves = movesForPos(network, pos, dij, stillClosed).toList()
            if (moves.isEmpty()) {
                return listOf(copy(
                    pos = List(pos.size) { "AA" },
                    minutesRemaining = 0,
                    releasedPressure = releasedPressure + open.sumOf { openValve ->
                        network.valves[openValve]!!.flowRate * minutesRemaining
                    }
                ))
            }
            return moves
        }

        private fun movesForPos(
            network: Network,
            remainingPos: List<String>,
            dij: List<Pair<String, Dijkstra.Output<String>>>,
            stillClosed: Set<String>,
            matches: List<Pair<String, Pair<String, Int>>> = emptyList(),
            unmoving: List<String> = emptyList()
        ): Sequence<State> {
            if (remainingPos.isEmpty() || stillClosed.isEmpty()) {
                if (matches.isEmpty()) {
                    return emptySequence()
                }

                val winner = matches.minBy { it.second.second }
                val newPos = matches.map { match ->
                    if (match == winner) match.second.first else {
                        val matchDij = dij.single { it.first == match.first }.second
                        val path = Dijkstra.assemblePath(matchDij.prev, match.first, match.second.first)!!
                        path.asSequence().take(winner.second.second + 1).last()
                    }
                } + remainingPos + unmoving
                return sequenceOf(copy(
                    pos = newPos.sorted(),
                    open = open + winner.second.first,
                    minutesRemaining = minutesRemaining - winner.second.second,
                    releasedPressure = releasedPressure + open.sumOf { openValve ->
                        network.valves[openValve]!!.flowRate * winner.second.second
                    }
                ))
            }

            val curPos = remainingPos.first()
            val otherPos = remainingPos.drop(1)
            val curDij = dij.single { it.first == curPos }.second
            val dist = stillClosed.mapNotNull { closedValve ->
                val valveDist = curDij.dist[closedValve]!!.toInt() + 1
                if (valveDist <= minutesRemaining) closedValve to valveDist else null
            }.toMap()
            return dist.entries.asSequence().flatMap { (closedValve, valveDist) ->
                movesForPos(
                    network,
                    otherPos,
                    dij,
                    stillClosed - closedValve,
                    matches + (curPos to (closedValve to valveDist)),
                    unmoving
                )
            } + if (dist.size < remainingPos.size) {
                movesForPos(network, otherPos, dij, stillClosed, matches, unmoving + curPos)
            } else emptySequence()
        }
    }

    private fun simulate(network: Network, initialState: State): Set<State> {
        var states = setOf(initialState)
        val finalStates = mutableSetOf<State>()
        while (states.isNotEmpty()) {
            val (nextStates, newFinalStates) = states.flatMap {
                it.nextMoves(network)
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
