package day02

import println
import readInput

fun main() {
    fun List<String>.countCube(color: day02.Color) = firstNotNullOfOrNull {
        it.removeSuffix(" ${color.name.lowercase()}").trim().toIntOrNull()
    } ?: 0

    fun List<String>.games() = map { line ->
        val (id, sets) = line.split(":")
        Game(id.removePrefix("Game ").toInt(), sets.split(";").map { set ->
            val colors = set.split(",")
            GameSet(
                red = colors.countCube(day02.Color.Red),
                green = colors.countCube(day02.Color.Green),
                blue = colors.countCube(day02.Color.Blue),
            )
        })
    }

    fun part1(lines: List<String>): Int {
        return lines.games().filter { game ->
            game.sets.all { set ->
                set.red <= 12 && set.green <= 13 && set.blue <= 14
            }
        }.sumOf { it.id }
    }

    fun part2(lines: List<String>): Int {
        return lines.games().sumOf { game ->
            val (r,g,b) = game.sets.fold(GameSet(0,0,0)) { set, next ->
                set.copy(
                    red = next.red.takeIf { it >= set.red } ?: set.red,
                    green = next.green.takeIf { it >= set.green } ?: set.green,
                    blue = next.blue.takeIf { it >= set.blue } ?: set.blue,
                )
            }
            r * g * b
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput(day = 2, test = true)
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput(day = 2)
    part1(input).println()
    part2(input).println()
}

private data class Game(val id: Int, val sets: List<GameSet>)
private data class GameSet(val red: Int, val blue: Int, val green: Int)
private enum class Color {
    Red, Green, Blue
}