package io.github.clechasseur.adventofcode.y2022

import kotlin.test.Test
import kotlin.test.assertEquals

class AdventOfCode2022 {
    class Day1Puzzles {
        @Test
        fun `day 1, part 1`() {
            assertEquals(73211, Day1.part1())
        }

        @Test
        fun `day 1, part 2`() {
            assertEquals(213958, Day1.part2())
        }
    }

    class Day2Puzzles {
        @Test
        fun `day 2, part 1`() {
            assertEquals(11666, Day2.part1())
        }

        @Test
        fun `day 2, part 2`() {
            assertEquals(12767, Day2.part2())
        }
    }
}