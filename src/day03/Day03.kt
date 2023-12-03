package day03

import println
import readInput
import kotlin.math.max
import kotlin.math.min

fun main() {

    fun List<String>.toArray() = map { line ->
        line.toCharArray()
    }.toTypedArray()

    fun List<String>.engineParts(): List<EnginePart> {
        return flatMapIndexed { x, line ->
            buildList {
                fun add(number: String, x: Int, y: Int) {
                    number.toIntOrNull()?.let { add(EnginePart(it, Coordinates(x, y - number.length..<y))) }
                }
                line.foldIndexed(String()) { y, acc, c ->
                    when {
                        c.isDigit() -> when {
                            acc.toIntOrNull() != null -> acc + c
                            else -> c.toString()
                        }
                        else -> {
                            add(acc, x, y)
                            c.toString()
                        }
                    }.also {
                        if (y == line.lastIndex) {
                            add(it, x, y)
                        }
                    }
                }
            }
        }
    }

    fun part1(lines: List<String>): Int {
        fun Array<CharArray>.hasAdjacentSymbols(regex: Regex, x: Int, y: Int): Boolean {
            for (i in max(0, x - 1)..min(lastIndex, x + 1)) {
                for (j in max(0, y - 1)..min(this[i].lastIndex, y + 1)) {
                    if (regex.matches(this[i][j].toString())) return true
                }
            }
            return false
        }

        val engineParts = lines.engineParts()
        val partArray = lines.toArray()
        return engineParts
            .filter {
                val (x, ys) = it.coordinates
                ys.any { y -> partArray.hasAdjacentSymbols(Regex("""[^\d.]"""), x, y) }
            }
            .sumOf { it.number }
    }

    fun part2(lines: List<String>): Int {
        fun EnginePart.isInTheNeighbourhood(x: Int, y: Int): Boolean {
            val (partX, partY) = coordinates
            return ((x - 1)..(x + 1)).any { partX == it } &&
                    ((y - 1)..(y + 1)).any { partY.contains(it) }
        }

        val engineParts = lines.engineParts().onEach { println(it) }
        val partArray = lines.toArray()
        return partArray.flatMapIndexed { x, line ->
            line.mapIndexed { y, c ->
                when (c) {
                    '*' -> engineParts.filter { part ->
                        part.isInTheNeighbourhood(x, y).also {
                             if (it )println("Found ${part} near $x,$y")
                        }
                    }.takeIf { it.size == 2 }?.run {
                        first().number * last().number
                    }
                    else -> null
                } ?: 0
            }
        }.sum()
            .also { println(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day = 3, test = true)
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput(day = 3)
    part1(input).println()
    part2(input).println()
}

private data class EnginePart(val number: Int, val coordinates: Coordinates)
private data class Coordinates(val x: Int, val y: IntRange)
