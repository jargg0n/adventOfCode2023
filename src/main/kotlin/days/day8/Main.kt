package days.day8

import java.io.File

fun main() {
    partTwo()
}

fun partOne() {
    val input = File("./src/main/kotlin/days/day8/input.txt").useLines { it.toList() }.joinToString(separator = "\n")
    val instructions = "" + Regex("""(\w+)\n""").findAll(input).first().groups[1]?.value
    val paths = Regex("""([A-Z]{3}).*?([A-Z]{3}).*?([A-Z]{3})""").findAll(input).map {
        ("" + it.groups[1]?.value) to listOf(("" + it.groups[2]?.value), ("" + it.groups[3]?.value))
    }.toList()

    var currentNode = "AAA"
    var steps = 0
    while (currentNode != "ZZZ") {
        currentNode = instructions.fold(currentNode) { node, instruction ->
            steps++
            val path = paths.first { it.first == node }
            if (instruction == 'L') return@fold path.second[0]
            path.second[1]
        }
    }
    println(steps)
}

var paths: List<Pair<String, List<String>>> = listOf()

fun directionToInt(direction: Char): Int {
    if (direction == 'L') return 0
    return 1
}

tailrec fun getStepsToZ(steps: Long, node: Pair<String, List<String>>, directions: String, directionsIndex: Int): Long {
    if (node.first.endsWith('Z')) {
        return steps
    }
    val newNode = paths.first { it.first == node.second[directionToInt(directions[directionsIndex])] }
    val newDirectionsIndex = (directionsIndex + 1) % directions.length
    return getStepsToZ(steps + 1, newNode, directions, newDirectionsIndex)
}

fun getLowestCommonMultiple(numbers: List<Long>): Long {
    var lcm: Long = numbers.max()
    while (true) {
        if (numbers.all { lcm % it == 0L }) {
            break
        }
        lcm += numbers.max()
    }
    return lcm
}

fun partTwo() {
    val input = File("./src/main/kotlin/days/day8/input.txt").useLines { it.toList() }.joinToString(separator = "\n")
    val instructions = "" + Regex("""(\w+)\n""").findAll(input).first().groups[1]?.value
    paths = Regex("""([0-9A-Z]{3}).*?([0-9A-Z]{3}).*?([0-9A-Z]{3})""").findAll(input).map {
        ("" + it.groups[1]?.value) to listOf(("" + it.groups[2]?.value), ("" + it.groups[3]?.value))
    }.toList()

    val startingNodeDistancesToZ = paths.filter { it.first.endsWith('A') }.map { getStepsToZ(0, it, instructions, 0) }

    println(getLowestCommonMultiple(startingNodeDistancesToZ))
}
