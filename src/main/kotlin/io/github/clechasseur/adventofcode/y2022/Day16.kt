package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.dij.Dijkstra
import io.github.clechasseur.adventofcode.dij.Graph
import io.github.clechasseur.adventofcode.y2022.data.Day16Data

object Day16 {
    private val input = Day16Data.input

    private val valveRegex = """^Valve ([A-Z]{2}) has flow rate=(\d+); tunnels? leads? to valves? ([A-Z]{2}(?:, [A-Z]{2})*)$""".toRegex()

    fun part1(): Int = simulate(
        Network(input.lines().map { it.toValve() }),
        State(open = emptySet(), minutesRemaining = 30, releasedPressure = 0, still = listOf("AA"))
    ).maxOf { it.releasedPressure }

    fun part2(): Int = simulate(
        Network(input.lines().map { it.toValve() }),
        State(open = emptySet(), minutesRemaining = 26, releasedPressure = 0, still = listOf("AA", "AA"))
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

    private data class Moving(val pos: String, val destValve: String, val path: List<String>)

    private data class State(
        val open: Set<String>,
        val minutesRemaining: Int,
        val releasedPressure: Int,
        val still: List<String>,
        val moving: Set<Moving> = emptySet()
    ) {
        fun nextMoves(network: Network): List<State> {
            require(minutesRemaining > 0) { "Stop getting next moves, we're done" }

            if (open == network.workingValves.keys) {
                return listOf(copy(
                    minutesRemaining = 0,
                    releasedPressure = releasedPressure + open.sumOf { openValve ->
                        network.valves[openValve]!!.flowRate * minutesRemaining
                    },
                    still = List(still.size) { "AA" },
                    moving = emptySet()
                ))
            }

            val newOpen = open.toMutableSet()
            val newStill = mutableListOf<String>()
            val newMoving = mutableSetOf<Moving>()

            moving.forEach { inMovement ->
                if (inMovement.path.isNotEmpty()) {
                    newMoving.add(inMovement.copy(
                        pos = inMovement.path.first(),
                        path = inMovement.path.drop(1)
                    ))
                } else {
                    newOpen.add(inMovement.destValve)
                    newStill.add(inMovement.pos)
                }
            }

            val possibleDestValves = network.workingValves.keys - newOpen
            val dij = still.distinct().associateWith { Dijkstra.build(network, it) }
            val possibleDestValvesByStill = still.associateWith { atRest ->
                possibleDestValves.filter { destValve ->
                    dij[atRest]!!.dist[destValve]!!.toInt() + 1 <= minutesRemaining
                }.toSet()
            }
            newStill.addAll(still.filter { atRest ->
                atRest in possibleDestValvesByStill.filterValues { it.isEmpty() }
            })

            val soonToBeMoving = still.filter { atRest ->
                atRest in possibleDestValvesByStill.filterValues { it.isNotEmpty() }
            }
            if (soonToBeMoving.isEmpty()) {
                return listOf(copy(
                    open = newOpen,
                    minutesRemaining = minutesRemaining - 1,
                    releasedPressure = releasedPressure + open.sumOf { openValve ->
                        network.valves[openValve]!!.flowRate
                    },
                    still = newStill.sorted(),
                    moving = newMoving
                ))
            }

            return nextMoving(soonToBeMoving, possibleDestValvesByStill, dij).map { (newMoves, newAtRest) ->
                copy(
                    open = newOpen,
                    minutesRemaining = minutesRemaining - 1,
                    releasedPressure = releasedPressure + open.sumOf { openValve ->
                        network.valves[openValve]!!.flowRate
                    },
                    still = (newStill + newAtRest).sorted(),
                    moving = newMoving + newMoves
                )
            }.toList()
        }

        private fun nextMoving(
            still: List<String>,
            possibleDestValvesByStill: Map<String, Set<String>>,
            dij: Map<String, Dijkstra.Output<String>>,
            usedValves: Set<String> = emptySet(),
            soFar: Pair<List<Moving>, List<String>> = emptyList<Moving>() to emptyList()
        ): Sequence<Pair<List<Moving>, List<String>>> = if (still.isNotEmpty()) {
            still.asSequence().flatMap { atRest ->
                val possibleDest = possibleDestValvesByStill[atRest]!! - usedValves
                possibleDest.asSequence().flatMap { destValve ->
                    val path = Dijkstra.assemblePath(dij[atRest]!!.prev, atRest, destValve)!!.drop(1)
                    val moving = Moving(path.first(), destValve, path.drop(1))
                    nextMoving(
                        still - atRest,
                        possibleDestValvesByStill,
                        dij,
                        usedValves + destValve,
                        (soFar.first + moving) to soFar.second
                    )
                } + if (possibleDest.size < still.size) {
                    nextMoving(
                        still - atRest,
                        possibleDestValvesByStill,
                        dij,
                        usedValves,
                        soFar.first to (soFar.second + atRest)
                    )
                } else emptySequence()
            }
        } else sequenceOf(soFar)
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
