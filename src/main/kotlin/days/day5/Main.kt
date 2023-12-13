package days.day5

import java.io.File

val numberRegex = Regex("""\d+""")

fun main() {
    partTwo()
}

fun partOne() {
    val lines = File("./src/main/kotlin/days/day5/input.txt").useLines { it.toList() }
    var seeds = numberRegex.findAll(lines[0]).toList().map { it.value.toLong() }.toMutableList()

    val getMapsRegex = Regex(""".+\n(?:\d+\s)+""")

    val allLines = lines.joinToString("\n")
    val maps = getMapsRegex.findAll(allLines).toList().map { it.value }

    seeds.forEachIndexed { index: Int, seed ->
        var tempSeed = seed
        maps.forEach { map ->
            val mapLines = map.split("\n")
            run lifeIsPain@{
                mapLines.forEach { mapLine ->
                    val numberMatches = numberRegex.findAll(mapLine);
                    if (numberMatches.count() > 0) {
                        val numbers = numberMatches.toList().map { number -> number.value.toLong() }

                        val destinationRangeStart = numbers[0]
                        val sourceRangeStart = numbers[1]
                        val rangeLength = numbers[2]

                        if (sourceRangeStart <= tempSeed && tempSeed < sourceRangeStart + rangeLength) {
                            tempSeed = destinationRangeStart + (tempSeed - sourceRangeStart)
                            return@lifeIsPain
                        }
                    }
                }
            }
        }
        seeds[index] = tempSeed
    }
    println(seeds.min())
}

fun partTwo() {
    val lines = File("./src/main/kotlin/days/day5/input.txt").useLines { it.toList() }
    val seeds = numberRegex.findAll(lines[0]).toList().map { it.value.toLong() }

    val getMapsRegex = Regex(""".+\n(?:\d+\s)+""")
    val allLines = lines.joinToString("\n")
    val mapsRaw = getMapsRegex.findAll(allLines).toList().map { it.value }

    val mapLineGroupings = mapsRaw.map { it.split("\n") }
    val maps = mapLineGroupings.map() { mapLineGrouping ->
        mapLineGrouping.map { listOfListsOfStringToListOfListsOfLongs(it) }.filter { mapLines -> mapLines.isNotEmpty() }
    }

    for (destination in 0 until Long.MAX_VALUE) {
            val potentialSeed = runMapsBackwards(destination, maps)
            val seedPairs = seeds.chunked(2).sortedBy { it[0] }
            if (seedPairs.any { seedPair ->potentialSeed in seedPair[0] until seedPair[0] + seedPair[1] }) {
                println(destination)
                return
            }
        }
    }

fun listOfListsOfStringToListOfListsOfLongs(mapLine: String): List<Long> {
    val numberMatches = numberRegex.findAll(mapLine)
    if (numberMatches.count() > 0) {
        return numberMatches.toList().map { number -> number.value.toLong() }
    }
    return listOf()
}

fun runMapsBackwards(destination: Long, maps: List<List<List<Long>>>): Long {
    var potentialSeed = destination
    maps.reversed().forEach { map ->
        potentialSeed = runMapReverse(potentialSeed, map)
    }
    return potentialSeed
}

fun runMapReverse(potentialSeed: Long, map: List<List<Long>>): Long {
    var tempSeed = potentialSeed
    run firstMatchingRange@{
        map.forEach { numbers ->
            val destinationRangeStart = numbers[0]
            val sourceRangeStart = numbers[1]
            val rangeLength = numbers[2]
            if (destinationRangeStart <= tempSeed && tempSeed < destinationRangeStart + rangeLength) {
                tempSeed = tempSeed + sourceRangeStart - destinationRangeStart
                return@firstMatchingRange
            }
        }
    }
    return tempSeed
}
