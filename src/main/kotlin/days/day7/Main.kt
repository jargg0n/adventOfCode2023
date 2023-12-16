package days.day7

import java.io.File

val highCardOrder = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
val highCardOrderPartTwo = listOf('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

fun main() {
    partTwo()
}

fun partOne() {
    val input = File("./src/main/kotlin/days/day7/input.txt").useLines { it.toList() }.toString()
    val handsToBets = Regex("""([2-9TJQKA]+)\s(\d+)""").findAll(input).map { it.groups[1]?.value.toString() to it.groups[2]?.value.toString().toInt() }.toMap()
    val handsToBetsToHandValuesSorted = handsToBets.map { it to getHandValue(it.key) }.sortedWith { first, second -> getStrongerHand(first, second, highCardOrder) }
    val sum = handsToBetsToHandValuesSorted.foldIndexed(0) { itr, sum, handToBetToHand ->
        sum + (handToBetToHand.first.value * (itr + 1)) }
    println(sum)
}

fun getHandValue(hand: String): Int {
    val orderedHand = sortCards(hand, highCardOrder)
    if (Regex("""([2-9TJQKA]).*?\1.*?\1.*?\1.*?\1""").findAll(orderedHand).count() > 0) return 6
    if (Regex("""([2-9TJQKA]).*?\1.*?\1.*?\1""").findAll(orderedHand).count() > 0) return 5
    if (Regex("""([2-9TJQKA])\1((?!\1)[2-9TJQKA])\2\2""").findAll(orderedHand).count() > 0) return 4
    if (Regex("""([2-9TJQKA])\1\1((?!\1)[2-9TJQKA])\2""").findAll(orderedHand).count() > 0) return 4
    if (Regex("""([2-9TJQKA]).*?\1.*?\1""").findAll(orderedHand).count() > 0) return 3
    if (Regex("""([2-9TJQKA])\1.*?([2-9TJQKA])\2""").findAll(orderedHand).count() > 0) return 2
    if (Regex("""([2-9TJQKA]).*?\1""").findAll(orderedHand).count() > 0) return 1
    return 0
}

fun getHandValuePartTwo(hand: String): Int {
    val handPermutations = mutableListOf(hand)
    if (hand.contains('J')) {
        val handUniqueWithoutJ = hand.toSet().filter { it != 'J' }
        handPermutations += handUniqueWithoutJ.map {
            hand.replace('J', it)
        }
    }
    return handPermutations.map { currentHand ->
        getHandValueFromOccurrences(currentHand.toSet().map { letter ->
            currentHand.count { it == letter }
        })
    }.max()
}

fun getHandValueFromOccurrences(occurrences: List<Int>): Int {
    if (occurrences.contains(5)) {
        return 6
    }
    if (occurrences.contains(4)) {
        return 5
    }
    if (occurrences.contains(3) && occurrences.contains(2)) {
        return 4
    }
    if (occurrences.contains(3)) {
        return 3
    }
    if (occurrences.count{ it == 2 } > 1) {
        return 2
    }
    if (occurrences.contains(2)) {
        return 1
    }
    return 0
}

fun sortCards(card: String, order: List<Char>): String {
    return card.toList().sortedBy { order.indexOf(it) }.joinToString(separator = "")
}

fun getStrongerHand(first: Pair<Map.Entry<String, Int>, Int>, second: Pair<Map.Entry<String, Int>, Int>, order: List<Char>): Int {
    if (first.second - second.second != 0) return first.second - second.second
    (first.first.key zip second.first.key).forEach {
        if (it.first != it.second) {
            return order.indexOf(it.first) - order.indexOf(it.second)
        }
    }
    return 0
}

fun partTwo() {
    val input = File("./src/main/kotlin/days/day7/input.txt").useLines { it.toList() }.toString()
    val handsToBets = Regex("""([2-9TJQKA]+)\s(\d+)""").findAll(input).map { it.groups[1]?.value.toString() to it.groups[2]?.value.toString().toInt() }.toMap()
    val handsToBetsToHandValuesSorted = handsToBets.map { it to getHandValuePartTwo(it.key) }.sortedWith { first, second -> getStrongerHand(first, second, highCardOrderPartTwo) }
    val sum = handsToBetsToHandValuesSorted.foldIndexed(0) { itr, sum, handToBetToHand ->
        sum + (handToBetToHand.first.value * (itr + 1)) }
    println(sum)
}
