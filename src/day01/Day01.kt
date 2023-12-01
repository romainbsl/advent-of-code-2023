fun main() {
    fun List<String>.sumOfBoundaries(): Int = sumOf { row ->
        val first = row.first { it.isDigit() }
        val last = row.last { it.isDigit() }
        "$first$last".toInt()
    }

    fun part1(input: List<String>): Int {
        return input.sumOfBoundaries()
    }

    fun part2(input: List<String>): Int {
        return input.map { row ->
            buildString {
                row.forEachIndexed { index, c ->
                    when {
                        c.isDigit() -> { append(c) }
                        else -> {
                            val sub = row.substring(startIndex = index)
                            numbers.forEach { (word, num) ->
                                if (sub.startsWith(word)) {
                                    append(num)
                                    return@forEach
                                }
                            }
                        }
                    }
                }
            }
        }.sumOfBoundaries()
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day = 1, test = true)
//        check(part1(testInput) == 142)
    check(part2(testInput) == 281)

    val input = readInput(day = 1)
    input.sumOfBoundaries().println()
    part2(input).println()
}

val numbers = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)
