fun main() {
    fun getCaloriesList(input: String): List<Int> {
        return input.split("\n\n")
            // here it is array of values like ["1000\n2000"]
            .map { it.split('\n').map(String::toInt).sum() }
            .sortedDescending()
    }

    fun part1(input: String): Int {
        val caloriesDescending = getCaloriesList(input)
        return caloriesDescending.first()
    }

    fun part2(input: String): Int {
        val caloriesDescending = getCaloriesList(input)
        return caloriesDescending.take(3).sum()
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInputText("Day01_test")


    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInputText("Day01")
    part1(input).println()
    part2(input).println()
}
