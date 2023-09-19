private fun parseRangesString(rangesString: String): List<List<Int>> {
    val rangeStrings = rangesString.split(',')
    return rangeStrings.map { str ->
        str.split('-').map { it.toInt() }
    }
}

private fun oneRangeContainedInOther(rangesString: String): Boolean {
    val (range1, range2) = parseRangesString(rangesString)
    return (range1[0] >= range2[0] && range1[1] <= range2[1]) || (range2[0] >= range1[0] && range2[1] <= range1[1])
}

private fun rangesOverlap(rangesString: String): Boolean {
    val (range1, range2) = parseRangesString(rangesString)
    return range1[0] <= range2[1] && range1[1] >= range2[0]
}

private fun part1(inputPlays: List<String>): Int {
    return inputPlays.count(::oneRangeContainedInOther)
}

private fun part2(inputPlays: List<String>): Int {
    return inputPlays.count(::rangesOverlap)
}

fun main() {
    println("======================================")
    println("Day 4 part 1 test input: ${part1(readInputLines("Day04_test"))}")
    println("Day 4 part 2 test input: ${part2(readInputLines("Day04_test"))}")
    println("Day 4 part 1 final input: ${part1(readInputLines("Day04"))}")
    println("Day 4 part 2 final input: ${part2(readInputLines("Day04"))}")
    println("======================================")
}