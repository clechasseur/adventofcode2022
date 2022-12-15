package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.util.Pt
import io.github.clechasseur.adventofcode.util.manhattan
import kotlin.math.sign

object Day15 {
    private val input = """
        Sensor at x=3844106, y=3888618: closest beacon is at x=3225436, y=4052707
        Sensor at x=1380352, y=1857923: closest beacon is at x=10411, y=2000000
        Sensor at x=272, y=1998931: closest beacon is at x=10411, y=2000000
        Sensor at x=2119959, y=184595: closest beacon is at x=2039500, y=-250317
        Sensor at x=1675775, y=2817868: closest beacon is at x=2307516, y=3313037
        Sensor at x=2628344, y=2174105: closest beacon is at x=3166783, y=2549046
        Sensor at x=2919046, y=3736158: closest beacon is at x=3145593, y=4120490
        Sensor at x=16, y=2009884: closest beacon is at x=10411, y=2000000
        Sensor at x=2504789, y=3988246: closest beacon is at x=3145593, y=4120490
        Sensor at x=2861842, y=2428768: closest beacon is at x=3166783, y=2549046
        Sensor at x=3361207, y=130612: closest beacon is at x=2039500, y=-250317
        Sensor at x=831856, y=591484: closest beacon is at x=-175938, y=1260620
        Sensor at x=3125600, y=1745424: closest beacon is at x=3166783, y=2549046
        Sensor at x=21581, y=3243480: closest beacon is at x=10411, y=2000000
        Sensor at x=2757890, y=3187285: closest beacon is at x=2307516, y=3313037
        Sensor at x=3849488, y=2414083: closest beacon is at x=3166783, y=2549046
        Sensor at x=3862221, y=757146: closest beacon is at x=4552923, y=1057347
        Sensor at x=3558604, y=2961030: closest beacon is at x=3166783, y=2549046
        Sensor at x=3995832, y=1706663: closest beacon is at x=4552923, y=1057347
        Sensor at x=1082213, y=3708082: closest beacon is at x=2307516, y=3313037
        Sensor at x=135817, y=1427041: closest beacon is at x=-175938, y=1260620
        Sensor at x=2467372, y=697908: closest beacon is at x=2039500, y=-250317
        Sensor at x=3448383, y=3674287: closest beacon is at x=3225436, y=4052707
    """.trimIndent()

    private val inputRegex = """^Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)$""".toRegex()

    fun part1(): Int {
        val arrangement = input.toArrangement()
        val minSensor = arrangement.sensors.keys.minBy { it.x }
        val maxSensor = arrangement.sensors.keys.maxBy { it.x }
        val minX = minSensor.x - manhattan(minSensor, arrangement.sensors[minSensor]!!)
        val maxX = maxSensor.x + manhattan(maxSensor, arrangement.sensors[maxSensor]!!)
        return (minX..maxX).map { Pt(it, 2_000_000) }.count { pt ->
            arrangement.sensors.entries.any { (sensor, beacon) ->
                manhattan(sensor, pt) <= manhattan(sensor, beacon)
            } && pt !in arrangement.beacons
        }
    }

    fun part2(): Long {
        val arrangement = input.toArrangement()
        val distressBeacon = arrangement.outsideEdges().filter {
            it.x in 0..4_000_000 && it.y in 0..4_000_000
        }.first {
            arrangement.notInRange(it)
        }
        return distressBeacon.x.toLong() * 4_000_000L + distressBeacon.y.toLong()
    }

    private data class Arrangement(val sensors: Map<Pt, Pt>) {
        val beacons: Collection<Pt>
            get() = sensors.values
        val ranges: Map<Pt, Int> = sensors.mapValues { (sensor, beacon) ->
            manhattan(sensor, beacon)
        }

        fun notInRange(pt: Pt): Boolean = ranges.all { (sensor, range) ->
            manhattan(sensor, pt) > range
        }

        fun outsideEdges(): Sequence<Pt> = ranges.asSequence().flatMap { (sensor, range) ->
            val a = sensor + Pt(-(range + 1), 0)
            val b = sensor + Pt(0, -(range + 1))
            val c = sensor + Pt(range + 1, 0)
            val d = sensor + Pt(0, range + 1)
            diagonal(a, b) + diagonal(b, c) + diagonal(c, d) + diagonal(d, a)
        }

        private fun diagonal(a: Pt, b: Pt): Sequence<Pt> {
            val displacement = Pt((b.x - a.x).sign, (b.y - a.y).sign)
            return generateSequence(a) { it + displacement }.takeWhile { it - displacement != b }
        }
    }

    private fun String.toArrangement(): Arrangement = Arrangement(lines().associate { line ->
        val match = inputRegex.matchEntire(line) ?: error("Invalid sensor declaration: $line")
        val (sensorX, sensorY, beaconX, beaconY) = match.destructured
        Pt(sensorX.toInt(), sensorY.toInt()) to Pt(beaconX.toInt(), beaconY.toInt())
    })
}
