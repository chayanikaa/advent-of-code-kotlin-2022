private const val dayNumber = 6

private fun getFirstDistinctWordPosition(input: String, nDistinctChars: Int): Int {
    val charDeque = ArrayDeque<Char>()
    for ((i, char) in input.withIndex()) {
        if (charDeque.size == nDistinctChars) {
            charDeque.removeFirstOrNull()
        }
        charDeque.add(char)
        val set = charDeque.toSet()
        if (set.size == nDistinctChars) {
            return i+1
        }
    }
    return Int.MAX_VALUE
}

private fun part1(input: String): Int = getFirstDistinctWordPosition(input, 4)

private fun part2(input: String): Int = getFirstDistinctWordPosition(input, 14)

fun main() {
    println("======================================")
    println("Day $dayNumber part 1 test input: ${part1("nppdvjthqldpwncqszvftbrmjlhg")}")
    println("Day $dayNumber part 1 final input: ${part1(readInputText("Day${dayNumber}"))}")
    println("Day $dayNumber part 2 final input: ${part2(readInputText("Day${dayNumber}"))}")
    println("======================================")
}