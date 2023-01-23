package io.github.clechasseur.adventofcode.y2022

import kotlin.test.Ignore
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

    class Day8Puzzles {
        @Test
        fun `day 8, part 1`() {
            assertEquals(1849, Day8.part1())
        }

        @Test
        fun `day 8, part 2`() {
            assertEquals(201600, Day8.part2())
        }
    }

    class Day9Puzzles {
        @Test
        fun `day 9, part 1`() {
            assertEquals(6503, Day9.part1())
        }

        @Test
        fun `day 9, part 2`() {
            assertEquals(2724, Day9.part2())
        }
    }

    class Day10Puzzles {
        @Test
        fun `day 10, part 1`() {
            assertEquals(14340L, Day10.part1())
        }

        @Test
        fun `day 10, part 2`() {
            val expected = """
                ###...##..###....##..##..###..#..#.###..
                #..#.#..#.#..#....#.#..#.#..#.#..#.#..#.
                #..#.#..#.#..#....#.#....###..####.#..#.
                ###..####.###.....#.#....#..#.#..#.###..
                #....#..#.#....#..#.#..#.#..#.#..#.#....
                #....#..#.#.....##...##..###..#..#.#....
            """.trimIndent()
            assertEquals(expected, Day10.part2())
        }
    }

    class Day11Puzzles {
        @Test
        fun `day 11, part 1`() {
            assertEquals(117640L, Day11.part1())
        }

        @Test
        fun `day 11, part 2`() {
            assertEquals(30616425600L, Day11.part2())
        }
    }

    class Day12Puzzles {
        @Test
        fun `day 12, part 1`() {
            assertEquals(504, Day12.part1())
        }

        @Test
        fun `day 12, part 2`() {
            assertEquals(500, Day12.part2())
        }
    }

    class Day13Puzzles {
        @Test
        fun `day 13, part 1`() {
            assertEquals(5675, Day13.part1())
        }

        @Test
        fun `day 13, part 2`() {
            assertEquals(20383, Day13.part2())
        }
    }

    class Day14Puzzles {
        @Test
        fun `day 14, part 1`() {
            assertEquals(614, Day14.part1())
        }

        @Test
        fun `day 14, part 2`() {
            assertEquals(26170, Day14.part2())
        }
    }

    class Day15Puzzles {
        @Test
        fun `day 15, part 1`() {
            assertEquals(4919281, Day15.part1())
        }

        @Test
        fun `day 15, part 2`() {
            assertEquals(12630143363767L, Day15.part2())
        }
    }

    class Day16Puzzles {
        @Test
        fun `day 16, part 1`() {
            assertEquals(1647, Day16.part1())
        }

        @Test
        @Ignore("Took 1.5 minutes to run on my machine")
        fun `day 16, part 2`() {
            assertEquals(2169, Day16.part2())
        }
    }

    class Day17Puzzles {
        @Test
        fun `day 17, part 1`() {
            assertEquals(3085, Day17.part1())
        }

        @Test
        fun `day 17, part 2`() {
            assertEquals(1535483870924L, Day17.part2())
        }
    }

    class Day18Puzzles {
        @Test
        fun `day 18, part 1`() {
            assertEquals(4450, Day18.part1())
        }

        @Test
        fun `day 18, part 2`() {
            assertEquals(2564, Day18.part2())
        }
    }

    class Day19Puzzles {
        @Test
        fun `day 19, part 1`() {
            assertEquals(1147, Day19.part1())
        }

        @Test
        @Ignore("Took about 7 minutes to run on my machine")
        fun `day 19, part 2`() {
            assertEquals(3080, Day19.part2())
        }
    }

    class Day20Puzzles {
        @Test
        fun `day 20, part 1`() {
            assertEquals(4914L, Day20.part1())
        }

        @Test
        fun `day 20, part 2`() {
            assertEquals(7973051839072L, Day20.part2())
        }
    }

    class Day21Puzzles {
        @Test
        fun `day 21, part 1`() {
            assertEquals(24947355373338L, Day21.part1())
        }

        @Test
        fun `day 21, part 2`() {
            assertEquals(3876907167495L, Day21.part2())
        }
    }

    class Day22Puzzles {
        @Test
        fun `day 22, part 1`() {
            assertEquals(57350, Day22.part1())
        }
    }
}
