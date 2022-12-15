package io.github.clechasseur.adventofcode.y2022

import io.github.clechasseur.adventofcode.util.Pt
import io.github.clechasseur.adventofcode.util.manhattan

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

    fun part1(): Int = input.toArrangement().coverage.filterKeys {
        it.y == 2_000_000
    }.count { (pt, id) ->
        id == Identifier.RANGE
    }

    private enum class Identifier {
        SENSOR,
        BEACON,
        RANGE,
    }

    private data class Arrangement(val sensors: Map<Pt, Pt>) {
        val beacons: Collection<Pt>
            get() = sensors.values

        val coverage: Map<Pt, Identifier>
            get() = sensors.keys.flatMap {
                coveredBySensor(it)
            }.associateWith {
                Identifier.RANGE
            } + sensors.keys.associateWith {
                Identifier.SENSOR
            } + beacons.associateWith {
                Identifier.BEACON
            }

        private fun coveredBySensor(sensor: Pt): Sequence<Pt> {
            val beacon = sensors[sensor]!!
            val dist = manhattan(sensor, beacon)
            return (sensor.x - dist..sensor.x + dist).asSequence().flatMap { x ->
                (sensor.y - dist..sensor.y + dist).asSequence().map { y -> Pt(x, y) }
            }.filter {
                manhattan(it, sensor) <= dist
            }
        }
    }

    private fun String.toArrangement(): Arrangement = Arrangement(lines().associate { line ->
        val match = inputRegex.matchEntire(line) ?: error("Invalid sensor declaration: $line")
        val (sensorX, sensorY, beaconX, beaconY) = match.destructured
        Pt(sensorX.toInt(), sensorY.toInt()) to Pt(beaconX.toInt(), beaconY.toInt())
    })
}
