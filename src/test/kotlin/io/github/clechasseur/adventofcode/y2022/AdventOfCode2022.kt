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

    class Day3Puzzles {
        @Test
        fun `day 3, part 1`() {
            assertEquals(7967, Day3.part1())
        }

        @Test
        fun `day 3, part 2`() {
            assertEquals(2716, Day3.part2())
        }
    }

    class Day4Puzzles {
        @Test
        fun `day 4, part 1`() {
            assertEquals(450, Day4.part1())
        }

        @Test
        fun `day 4, part 2`() {
            assertEquals(837, Day4.part2())
        }
    }

    class Day5Puzzles {
        @Test
        fun `day 5, part 1`() {
            assertEquals("VPCDMSLWJ", Day5.part1())
        }

        @Test
        fun `day 5, part 2`() {
            assertEquals("TPWCGNCCG", Day5.part2())
        }
    }

    class Day6Puzzles {
        @Test
        fun `day 6, part 1`() {
            assertEquals(1042, Day6.part1())
        }

        @Test
        fun `day 6, part 2`() {
            assertEquals(1042, Day6.part2())
        }
    }

    class Day7Puzzles {
        @Test
        fun `day 7, part 1`() {
            assertEquals(1432936L, Day7.part1())
        }

        @Test
        fun `day 7, part 2`() {
            assertEquals(272298L, Day7.part2())
        }
    }
}
