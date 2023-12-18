package days.day2

import java.io.File

fun main() {
    partTwo()
}

fun partOne() {
    val lines = File("./src/main/kotlin/days/day2/input.txt").readLines()

    val redRegex = Regex("""(\d+) red""")
    val greenRegex = Regex("""(\d+) green""")
    val blueRegex = Regex("""(\d+) blue""")

    val possibleGames = lines.map { line ->
        redRegex.findAll(line).all { (it.groups[1]?.value?.toInt() ?: 0) <= 12 }
                && greenRegex.findAll(line).all { (it.groups[1]?.value?.toInt() ?: 0) <= 13 }
                && blueRegex.findAll(line).all { (it.groups[1]?.value?.toInt() ?: 0) <= 14 }
    }

    val score = possibleGames.foldIndexed(0) { index, sum, game ->
        if (game) return@foldIndexed sum + index + 1
        return@foldIndexed sum
    }

    println(score)
}

fun partTwo() {
    val lines = File("./src/main/kotlin/days/day2/input.txt").readLines()

    val redRegex = Regex("""(\d+) red""")
    val greenRegex = Regex("""(\d+) green""")
    val blueRegex = Regex("""(\d+) blue""")

    val games = lines.map { line ->
        redRegex.findAll(line).map { (it.groups[1]?.value?.toInt() ?: 0) }.max() *
        greenRegex.findAll(line).map { (it.groups[1]?.value?.toInt() ?: 0) }.max() *
        blueRegex.findAll(line).map { (it.groups[1]?.value?.toInt() ?: 0) }.max()
    }

    println(games.sum())
}
