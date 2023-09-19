const val dayNumber = 5
private operator fun <T> List<T>.component6(): T = get(5)
class Instruction(input: String) {
    val nCrates: Int
    val source: Int
    val destination: Int
    init {
        val parts = input.split(' ');
        nCrates = parts[1].toInt()
        source = parts[3].toInt()
        destination = parts[5].toInt()
    }
}

data class Day05Input(val stacks: List<ArrayDeque<Char>>, val instructions: List<Instruction>)

fun parseStacks(input: String): List<ArrayDeque<Char>> {
    val lines = input.split('\n')
    val numberOfStacks = lines.last().trim().count { it.isDigit() }

    // Initialize empty stacks
    val stacks = MutableList(numberOfStacks) { mutableListOf<Char>() }

    for (i in 0..lines.last().length) {
        val stackIndex = ( i - 1 ) / 4
        for (j in 0 until lines.size - 1) {
            when(val currentChar = lines.getOrNull(j)?.getOrNull(i)) {
                '[',']',' ',null->continue
                else -> stacks[stackIndex].add(currentChar)
            }
        }
    }

    return stacks.map { ArrayDeque(it.reversed()) }
}

fun executeInstructionPart1(instruction: Instruction, stacks: List<ArrayDeque<Char>>) {
    val sourceStack = stacks[instruction.source - 1]
    val destinationStack = stacks[instruction.destination - 1]

    repeat(instruction.nCrates) {
        destinationStack.add(sourceStack.removeLast())
    }
}

fun executeInstructionPart2(instruction: Instruction, stacks: List<ArrayDeque<Char>>) {
    val sourceStack = stacks[instruction.source - 1]
    val destinationStack = stacks[instruction.destination - 1]
    val itemsToMove = List(instruction.nCrates) { sourceStack.removeLast() }

    destinationStack.addAll(itemsToMove.reversed())
}

fun parseInput(input: String): Day05Input {
    val parts = input.split("\n\n")
    val stackPart = parts.first()
    val instructionPart = parts.last()

    val instructions = instructionPart.split('\n').map(::Instruction)
    val stacks = parseStacks(stackPart)

    return Day05Input(stacks, instructions)
}

private fun part1(input: String): String {
    val parsedInput = parseInput(input)
    for (instruction in parsedInput.instructions) {
        executeInstructionPart1(instruction, parsedInput.stacks)
    }
    val topCratesString = parsedInput.stacks.map{it.last()}.joinToString("")

    println(topCratesString)
    return topCratesString
}

private fun part2(input: String): String {
    val parsedInput = parseInput(input)
    for (instruction in parsedInput.instructions) {
        executeInstructionPart2(instruction, parsedInput.stacks)
    }
    val topCratesString = parsedInput.stacks.map{it.last()}.joinToString("")

    println(topCratesString)
    return topCratesString
}

fun main() {
    println("======================================")
    println("Day $dayNumber part 1 test input: ${part1(readInputText("Day${dayNumber}_test"))}")
    println("Day $dayNumber part 2 test input: ${part2(readInputText("Day${dayNumber}_test"))}")
    println("Day $dayNumber part 1 final input: ${part1(readInputText("Day${dayNumber}"))}")
    println("Day $dayNumber part 2 final input: ${part2(readInputText("Day${dayNumber}"))}")
    println("======================================")
}