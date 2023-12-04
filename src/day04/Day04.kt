package day04

import println
import readInput

fun main() {

    fun String.cardWinningValues(): Set<Int> {
        val cardNumbers = substringAfter(": ").split(" | ")
        val winningNumbers = cardNumbers.first().split(" ").mapNotNull { it.toIntOrNull() }
        val values = cardNumbers.last().split(" ").mapNotNull { it.toIntOrNull() }
        return values.intersect(winningNumbers.toSet())
    }

    fun Int.countPoints(n : Int) = (1..<n).fold(1) { acc, _ -> acc * 2 }

    fun part1(lines: List<String>): Int {
        return lines.sumOf { line ->
            val winningValues = line.cardWinningValues()
            when {
                winningValues.isNotEmpty() -> 2.countPoints(winningValues.size)
                else -> 0
            }
        }
    }

    fun part2(lines: List<String>): Int {
        return lines
            .map(String::cardWinningValues)
            .foldIndexed(IntArray(lines.size) { 1 }) { index, acc, winningValues ->
                repeat(winningValues.size) { copyIndex ->
                    acc[index + 1 + copyIndex] += acc[index]
                }
                acc
            }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day = 4, test = true)
    check(part1(testInput).println() == 13)
    check(part2(testInput).println() == 30)

    val input = readInput(day = 4)
    check(part1(input).println() == 26218)
    check(part2(input).println() == 9997537)
}
