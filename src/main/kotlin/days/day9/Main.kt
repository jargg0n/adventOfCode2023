package days.day9

import java.io.File

fun main() {
    partTwo()
}

fun partOne() {
    val histories = File("./src/main/kotlin/days/day9/input.txt").readLines().map {
        Regex("""(-*\w+)""").findAll(it).map { match -> match.groups[1]?.value?.toLong() ?: -1 }.toList()
    }
    println(histories.fold(0L) { acc, next ->
        acc + getDerivative(next) })
}

fun getDerivative(history: List<Long>): Long {
    val derivatives = mutableListOf(history)
    do {
        val newDerivative = derivatives.last().mapIndexed { index, next ->
            if (index < derivatives.last().size - 1) {
                return@mapIndexed derivatives.last()[index + 1] - next
            } else {
                -1
            }
        }.dropLast(1)

        if (newDerivative.isNotEmpty()) {
            derivatives.add(newDerivative)
        } else {
            derivatives.add(listOf(0L))
        }
    } while (!derivatives.last().all { it == 0L })

    return derivatives.fold(0L) { acc, next ->
        next.last() + acc
    }
}

fun partTwo() {
    val histories = File("./src/main/kotlin/days/day9/input.txt").readLines().map {
        Regex("""(-*\w+)""").findAll(it).map { match -> match.groups[1]?.value?.toLong() ?: -1 }.toList()
    }
    println(histories.fold(0L) { acc, next ->
        acc + getDerivativeBackwards(next) })
}

fun getDerivativeBackwards(history: List<Long>): Long {
    val derivatives = mutableListOf(history)
    do {
        val newDerivative = derivatives.last().mapIndexed { index, next ->
            if (index < derivatives.last().size - 1) {
                return@mapIndexed derivatives.last()[index + 1] - next
            } else {
                -1
            }
        }.dropLast(1)

        if (newDerivative.isNotEmpty()) {
            derivatives.add(newDerivative)
        } else {
            derivatives.add(listOf(0L))
        }
    } while (!derivatives.last().all { it == 0L })

    return derivatives.reversed().fold(0L) { acc, next ->
        next.first() - acc
    }
}
