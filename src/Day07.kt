private const val dayNumber = 7

sealed class Command(open val commandString: String, open val currentDirectory: DirectoryContent.Directory) {
    abstract fun execute(): CommandResult
    class ChangeDirectoryCommand(
        override val commandString: String,
        override val currentDirectory: DirectoryContent.Directory,
    ) : Command(commandString, currentDirectory) {
        override fun execute(): CommandResult {
            // println("here CD $commandString")
            val newDirName = commandString.split(" ")[1]
            val existingDirectory = currentDirectory.children.find {
                it.name == newDirName && it is DirectoryContent.Directory
            } as DirectoryContent.Directory?

            val currDir = when {
                newDirName == ".." && currentDirectory.parent != null -> currentDirectory.parent
                existingDirectory != null -> existingDirectory
                else -> {
                    val newDir = DirectoryContent.Directory(newDirName, currentDirectory, mutableListOf())
                    currentDirectory.children = mutableListOf(newDir)
                    newDir
                }
            }

            return CommandResult(currDir)
        }
    }

    class ListDirectoryCommand(
        override val commandString: String,
        override val currentDirectory: DirectoryContent.Directory,
    ) : Command(commandString, currentDirectory) {
        override fun execute(): CommandResult {

            val directoryContents = commandString.split("\n").drop(1)
            currentDirectory.children = directoryContents.map {
                val parts = it.split(" ")
                when {
                    it.startsWith("dir") -> DirectoryContent.Directory(parts[1], currentDirectory, listOf())
                    else -> DirectoryContent.File(parts[1], currentDirectory, parts[0].toInt())
                }
            }

            return CommandResult(currentDirectory)
        }
    }

    companion object {
        operator fun invoke (commandString: String, currentDirectory: DirectoryContent.Directory): Command {
            return when {
                commandString.startsWith("cd") -> ChangeDirectoryCommand(commandString, currentDirectory)
                else -> ListDirectoryCommand(commandString, currentDirectory)
            }
        }
    }
}

class CommandResult(val currentDirectory: DirectoryContent.Directory) {}

sealed class DirectoryContent(open val name: String, open val parent: Directory?) {
    class Directory(
        override val name: String,
        override val parent: Directory?,
        var children: List<DirectoryContent>,
    ) : DirectoryContent(
        name,
        parent
    ) {
        fun calculateSize(): Int {
            return children.fold(0) { size, curr ->
                when (curr) {
                    is File -> size + curr.size
                    is Directory -> size + curr.calculateSize()
                }
            }
        }

        fun computeChildDirectoriesWithSize(list: MutableList<Pair<Directory, Int>> = mutableListOf()): Int {
            return children.fold(0) { size, curr ->
                when (curr) {
                    is File -> size + curr.size
                    is Directory -> {
                        val dirSize = curr.computeChildDirectoriesWithSize(list)
                        list.add(Pair(curr, dirSize))
                        size + dirSize
                    }
                    }
                }
            }
        }
    class File(override val name: String, override val parent: Directory?, val size: Int) :
        DirectoryContent(name, parent)
}

private fun executeCommand(commandString: String, currentDirectory: DirectoryContent.Directory): CommandResult {
    val command =  Command(commandString, currentDirectory)
    return command.execute()
}

private fun executeCommands(input: String): DirectoryContent.Directory {
    val commandStrings = input.split("$ ").filter{ it.isNotBlank() }.map{ it.trim() }

    val initDir = DirectoryContent.Directory("/", null, children = listOf())
    var commandResult = CommandResult(initDir)
    commandStrings.forEach {
        commandResult = executeCommand(it, commandResult.currentDirectory)
    }

    return initDir
}

private fun part1(input: String): Int {
    val mainDir = executeCommands(input)
    val resultList = mutableListOf<Pair<DirectoryContent.Directory, Int>>()
    mainDir.computeChildDirectoriesWithSize(resultList)

    return resultList.filter{ it.second <= 100000 }.sumOf { it.second }
}

private fun part2(input: String): Int {
    val totalSpace = 70000000
    val requiredSpaceForUpdate = 30000000

    val mainDir = executeCommands(input)
    val resultList = mutableListOf<Pair<DirectoryContent.Directory, Int>>()
    mainDir.computeChildDirectoriesWithSize(resultList)

    val totalOccupiedSize = mainDir.calculateSize()
    val toFreeupSpace = requiredSpaceForUpdate - (totalSpace - totalOccupiedSize)

    val sizeToDelete = resultList.fold(Int.MAX_VALUE) {
        dirSizeToDelete, currDirSize ->
            if (currDirSize.second in toFreeupSpace until dirSizeToDelete) currDirSize.second else dirSizeToDelete
    }
    return sizeToDelete
}

fun main() {
    println("======================================")
    println("Day $dayNumber part 1 test input: ${part1(readInputText("Day${dayNumber}_test"))}")
    println("Day $dayNumber part 1 final input: ${part1(readInputText("Day${dayNumber}"))}")
    println("Day $dayNumber part 2 test input: ${part2(readInputText("Day${dayNumber}_test"))}")
    println("Day $dayNumber part 2 final input: ${part2(readInputText("Day${dayNumber}"))}")
    println("======================================")
}

// This solution probably can be a lot nicer looking
// Need to figure out the mutability here
// ALso memoize the directory sizes while computing the tree is probably more efficient
// All these classes are living in one file because it's AOC, of course it's probably better to split them up