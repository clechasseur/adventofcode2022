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
    ).releasedPressure

    fun part2(): Int = simulate(
        Network(input.lines().map { it.toValve() }),
        State(pos = listOf("AA", "AA"), open = emptySet(), minutesRemaining = 26, releasedPressure = 0)
    ).releasedPressure

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
        fun nextMove(network: Network): State {
            if (minutesRemaining == 0) {
                return this
            }

            val dij = pos.distinct().associateWith { Dijkstra.build(network, it) }
            val stillClosed = (network.workingValves.keys - open).toMutableSet()
            val moves = mutableListOf<Move>()
            val unmoving = mutableListOf<String>()
            pos.forEach { curPos ->
                val bestMove = stillClosed.mapNotNull { destValve ->
                    val dist = dij[curPos]!!.dist[destValve]!!.toInt() + 1
                    if (dist <= minutesRemaining) Move(curPos, destValve, dist) else null
                }.maxByOrNull { move ->
                    simulate(network, copy(
                        pos = (pos - curPos + move.destValve).sorted(),
                        open = open + move.destValve,
                        minutesRemaining = minutesRemaining - move.dist,
                        releasedPressure = releasedPressure + open.sumOf { openValve ->
                            network.valves[openValve]!!.flowRate * move.dist
                        }
                    ), false).releasedPressure
                }
                if (bestMove != null) {
                    moves.add(bestMove)
                    stillClosed.remove(bestMove.destValve)
                } else {
                    unmoving.add(curPos)
                }
            }

            if (moves.isEmpty()) {
                return copy(
                    pos = List(pos.size) { "AA" },
                    minutesRemaining = 0,
                    releasedPressure = releasedPressure + open.sumOf { openValve ->
                        network.valves[openValve]!!.flowRate * minutesRemaining
                    }
                )
            }

            val nextMoveDist = moves.minOf { it.dist }
            val nextMoves = moves.filter { it.dist == nextMoveDist }
            val newPos = moves.map { move ->
                if (move in nextMoves) move.destValve else {
                    val moveDij = dij[move.pos]!!
                    val path = Dijkstra.assemblePath(moveDij.prev, move.pos, move.destValve)!!
                    path.take(nextMoveDist + 1).last()
                }
            } + unmoving
            return copy(
                pos = newPos.sorted(),
                open = open + nextMoves.map { it.destValve },
                minutesRemaining = minutesRemaining - nextMoveDist,
                releasedPressure = releasedPressure + open.sumOf { openValve ->
                    network.valves[openValve]!!.flowRate * nextMoveDist
                }
            )
        }

        private data class Move(val pos: String, val destValve: String, val dist: Int)
    }

    private fun simulate(network: Network, initialState: State, print: Boolean = true): State = generateSequence(initialState) {
        it.nextMove(network)
    }.filter {
        if (print) {
            println(it)
        }
        true
    }.dropWhile {
        it.minutesRemaining > 0
    }.first()

    private fun String.toValve(): Valve {
        val match = valveRegex.matchEntire(this) ?: error("Wrong valve spec: $this")
        val (id, flowRate, tunnels) = match.destructured
        return Valve(id, flowRate.toInt(), tunnels.split(", "))
    }
}
