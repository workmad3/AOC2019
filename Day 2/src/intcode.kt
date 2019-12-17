val initialState: List<Int> = listOf(1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,6,1,19,2,19,13,23,1,23,10,27,1,13,27,31,2,31,10,35,1,35,9,39,1,39,13,43,1,13,43,47,1,47,13,51,1,13,51,55,1,5,55,59,2,10,59,63,1,9,63,67,1,6,67,71,2,71,13,75,2,75,13,79,1,79,9,83,2,83,10,87,1,9,87,91,1,6,91,95,1,95,10,99,1,99,13,103,1,13,103,107,2,13,107,111,1,111,9,115,2,115,10,119,1,119,5,123,1,123,2,127,1,127,5,0,99,2,14,0,0)

fun processCode(program: MutableList<Int>, currentPosition: Int) {
    val code = program[currentPosition]
    val in1 = program[currentPosition + 1]
    val in2 = program[currentPosition + 2]
    val out = program[currentPosition + 3]
    if (code == 1) {
        program[out] = program[in1] + program[in2]
    } else if (code == 2) {
        program[out] = program[in1] * program[in2]
    }
}

fun runProgram(noun: Int, verb: Int): List<Int> {
    var currentPosition = 0
    val program = initialState.toMutableList()
    program[1] = noun
    program[2] = verb
    while (program[currentPosition] != 99) {
        processCode(program, currentPosition)
        currentPosition += 4
    }
    return program
}

fun main() {
    var noun: Int = 0
    var verb: Int = 0
    var output: Int = 0
    while (output != 19690720) {
        verb += 1
        if (verb > 99) {
            noun += 1
            verb = 0
        }
       output = runProgram(noun, verb)[0]
    }
    print((noun * 100 + verb).toString())
}