private fun calculateCommonCharScore3Elves(inputs: List<String>): Int {
    val charSets = inputs.map{it.toSet()}
    val commonCharSet = charSets.reduce { acc, curr -> acc.intersect(curr) }
    return calculateCode(commonCharSet.first())
}

private fun calculateCommonCharScore(input: String): Int {
    if (input.length <= 1) return 0

    val charSets = input.chunked(input.length / 2) {it.toSet()}
    val commonCharSet = charSets.reduce { acc, curr -> acc.intersect(curr) }
    return calculateCode(commonCharSet.first())
}

private fun calculateCode(char: Char): Int =
    when (char) {
        in 'a'..'z' -> char.code - 96
        else -> char.code - 38
    }


private fun part1(inputPlays: List<String>): Int {
    return inputPlays.sumOf { calculateCommonCharScore(it) }
}

private fun part2(inputPlays: List<String>): Int {
    val elfGroups = inputPlays.chunked(3)
    return elfGroups.sumOf { calculateCommonCharScore3Elves(it) }
}

fun main() {
    println("======================================")
    println("Day 3 part 1 test input: ${part1(readInputLines("Day03_test"))}")
    println("Day 3 part 2 test input: ${part2(readInputLines("Day03_test"))}")
    println("Day 3 part 1 final input: ${part1(readInputLines("Day03"))}")
    println("Day 3 part 2 final input: ${part2(readInputLines("Day03"))}")
    println("======================================")
}