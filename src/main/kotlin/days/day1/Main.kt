package days.day1

import java.io.File

fun main() {
    partTwo()
}

fun partOne() {
    val lines = File("./src/main/kotlin/days/day1/input.txt").readLines()
    var sum = 0;
    lines.forEach {
        val regex = """(\d)""".toRegex()
        val numbers = regex.findAll(it)

        val firstNumber = numbers.first().value
        val lastNumber = numbers.last().value
        sum += (firstNumber + lastNumber).toInt()
    }
    println(sum)
}

fun partTwo() {
    val lines = File("./src/main/kotlin/days/day1/input.txt").readLines()
    val words: HashMap<String, Int> = hashMapOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)
    var sum = 0;
    lines.forEach {
        val regex = """(?=(\d|one|two|three|four|five|six|seven|eight|nine))""".toRegex()
        val numbers = regex.findAll(it)
        val firstNumber: String = words.getOrDefault(numbers.first().groups[1]?.value, numbers.first().groups[1]?.value).toString()
        val lastNumber: String = words.getOrDefault(numbers.last().groups[1]?.value, numbers.last().groups[1]?.value).toString()
        sum += (firstNumber + "" + lastNumber).toInt()
    }
    println(sum)
}