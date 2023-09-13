enum class MoveTypes(val elfCode: Char, val score: Int) {
    Rock('A', 1),
    Paper('B', 2),
    Scissors('C', 3);

    companion object {
        private val map = MoveTypes.values().associateBy { it.elfCode }
        operator fun get(value: Char) = map[value]
    }
}

enum class MoveTypesElf2Part1(val elfCode: Char, val score: Int) {
    Rock('X', 1),
    Paper('Y', 2),
    Scissors('Z', 3);

    companion object {
        private val map = MoveTypesElf2Part1.values().associateBy { it.elfCode }
        operator fun get(value: Char) = map[value]
    }
}

enum class ResultScores(val score: Int, val elf2Code: Char) {
    Win(6, 'Z'),
    Draw(3, 'Y'),
    Lose(0, 'X');

    companion object {
        private val map = ResultScores.values().associateBy { it.elf2Code }
        operator fun get(value: Char) = map[value]
    }
}

data class Score(val elf1: Int, val elf2: Int)

fun calculateScoresForRoundPart1(play: String): Score {
    val elf2Move = MoveTypesElf2Part1[play[2]]
    val elf1Move = MoveTypes[play[0]] // opponent


    val winLoseScoreElf1: Int = when (elf1Move) {
        MoveTypes.Paper -> when (elf2Move) {
            MoveTypesElf2Part1.Paper -> 3
            MoveTypesElf2Part1.Rock -> 6
            MoveTypesElf2Part1.Scissors -> 0
            null -> TODO("Extraneous value for elf2")
        }
        MoveTypes.Rock -> when (elf2Move) {
            MoveTypesElf2Part1.Paper -> 0
            MoveTypesElf2Part1.Rock -> 3
            MoveTypesElf2Part1.Scissors -> 6
            null -> TODO("Extraneous value for elf2")
        }
        MoveTypes.Scissors -> when (elf2Move) {
            MoveTypesElf2Part1.Paper -> 6
            MoveTypesElf2Part1.Rock -> 0
            MoveTypesElf2Part1.Scissors -> 3
            null -> TODO("Extraneous value for elf2")
        }
        null -> TODO("Extraneous value for elf1")
    }

    return Score(elf1Move.score + winLoseScoreElf1, elf2Move.score + (6 - winLoseScoreElf1))
}

fun calculateScoresForRoundPart2(play: String): Score {
    val elf1Move = MoveTypes[play[0]]
    val winLoseScoreElf2 = ResultScores[play[2]]

    val elf2Move: MoveTypes = when (elf1Move) {
        MoveTypes.Paper -> when (winLoseScoreElf2) {
            ResultScores.Win -> MoveTypes.Scissors
            ResultScores.Draw -> MoveTypes.Paper
            ResultScores.Lose -> MoveTypes.Rock
            null -> TODO("Extraneous value for Win Lose code")

        }
        MoveTypes.Rock -> when (winLoseScoreElf2) {
            ResultScores.Win -> MoveTypes.Paper
            ResultScores.Draw -> MoveTypes.Rock
            ResultScores.Lose -> MoveTypes.Scissors
            null -> TODO("Extraneous value for Win Lose code")
        }
        MoveTypes.Scissors -> when (winLoseScoreElf2) {
            ResultScores.Win -> MoveTypes.Rock
            ResultScores.Draw -> MoveTypes.Scissors
            ResultScores.Lose -> MoveTypes.Paper
            null -> TODO("Extraneous value for Win Lose code")
        }
        null -> TODO("Extraneous value for elf1 move")
    }

    return Score(elf1Move.score + (6 - winLoseScoreElf2.score), elf2Move.score + winLoseScoreElf2.score)
}

fun part1(inputPlays: List<String>): Int {
    // elf 1 is opponent
    return inputPlays.sumOf { calculateScoresForRoundPart1(it).elf2 }
}

fun part2(inputPlays: List<String>): Int {
    // elf 1 is opponent
    return inputPlays.sumOf { calculateScoresForRoundPart2(it).elf2 }
}

fun main() {
    println("======================================")
    println("Day 2 part 1 test input: ${part1(readInputLines("Day02_test"))}")
    println("Day 2 part 2 test input: ${part2(readInputLines("Day02_test"))}")
    println("Day 2 part 1 final input: ${part1(readInputLines("Day02"))}")
    println("Day 2 part 2 final input: ${part2(readInputLines("Day02"))}")
    println("======================================")
}