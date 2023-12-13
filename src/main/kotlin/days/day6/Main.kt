package days.day6

import java.io.File

fun main() {
    partTwo()
}

fun partOne() {
    val input = File("./src/main/kotlin/days/day6/input.txt").useLines { it.toList() }
    val numberRegex = Regex("""\d+""")
    val times = numberRegex.findAll(input[0]).toList().map { it.value.toInt() }
    val distances = numberRegex.findAll(input[1]).toList().map { it.value.toInt() }
    val productOfWaysToWinEachRace = times.zip(distances) { time, distance ->
        listOf(time, distance)
    }.fold(1) { product, timeDistance -> product * getNumberOfWinningCombinations(timeDistance) }

    println(productOfWaysToWinEachRace)
}

fun getNumberOfWinningCombinations(timeDistance: List<Int>): Int {
    val time = timeDistance[0]
    val distance = timeDistance[1]
    val winningCombinations = (1..<time).fold(0) { sum, tempTime ->
        if (tempTime * (time - tempTime) > distance) {
            return@fold 1 + sum
        }
        return@fold sum
    }
    return winningCombinations
}

fun partTwo() {
    val input = File("./src/main/kotlin/days/day6/input.txt").useLines { it.toList() }
    val numberRegex = Regex("""\d+""")
    val time = numberRegex.findAll(input[0]).toList().map { it.value.toInt() }.joinToString(separator = "").toLong()
    val distance = numberRegex.findAll(input[1]).toList().map { it.value.toInt() }.joinToString(separator = "").toLong()
    val firstWinTime = (1..<time).first { tempTime -> tempTime * (time - tempTime) > distance }
    println(time - (2 * firstWinTime) + 1)
}
