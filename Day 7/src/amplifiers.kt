import org.apache.commons.collections4.iterators.PermutationIterator

val initialState: List<Int> = listOf(3,8,1001,8,10,8,105,1,0,0,21,34,55,68,85,106,187,268,349,430,99999,3,9,1001,9,5,9,1002,9,5,9,4,9,99,3,9,1002,9,2,9,1001,9,2,9,1002,9,5,9,1001,9,2,9,4,9,99,3,9,101,3,9,9,102,3,9,9,4,9,99,3,9,1002,9,5,9,101,3,9,9,102,5,9,9,4,9,99,3,9,1002,9,4,9,1001,9,2,9,102,3,9,9,101,3,9,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,1,9,4,9,99,3,9,1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,99)

class IntComp(private val program: MutableList<Int>, private val phase: Int) {
    var nextInput = 0
    var running = true
    var amp:IntComp? = null
    var output = 0

    private var currentPosition = 0
    private var pause = false
    private var readPhase = false

    private fun readInput():Int {
        if (readPhase) {
            return nextInput
        }
        readPhase = true
        return phase
    }

    private fun writeOutput(value: Int) {
        amp?.nextInput = value
        output = value
        pause = true
    }

    private fun readParam(value:Int, mode:Int):Int {
        val immediate:Boolean = (mode == 1)

        if (immediate) {
            return value
        }
        return program[value]
    }

    private fun processCode():Int {
        val code = program[currentPosition] % 100
        val params = program[currentPosition] / 100

        var nextPosition = currentPosition
        when(code) {
            1 -> {
                val in1 = program[currentPosition + 1]
                val in1Mode = params % 10
                val in2 = program[currentPosition + 2]
                val in2Mode = params / 10
                val out = program[currentPosition + 3]
                program[out] = readParam(in1, in1Mode) + readParam(in2, in2Mode)
                nextPosition += 4
            }
            2 -> {
                val in1 = program[currentPosition + 1]
                val in1Mode = params % 10
                val in2 = program[currentPosition + 2]
                val in2Mode = params / 10
                val out = program[currentPosition + 3]
                program[out] = readParam(in1, in1Mode) * readParam(in2, in2Mode)
                nextPosition += 4
            }
            3 -> {
                val out = program[currentPosition + 1]
                program[out] = readInput()
                nextPosition += 2
            }
            4 -> {
                val in1 = program[currentPosition + 1]
                val in1Mode = params % 10
                writeOutput(readParam(in1, in1Mode))
                nextPosition += 2
            }
            5 -> {
                val in1 = program[currentPosition + 1]
                val in1Mode = params % 10
                val in2 = program[currentPosition + 2]
                val in2Mode = params / 10
                if (readParam(in1, in1Mode) != 0) {
                    nextPosition = readParam(in2, in2Mode)
                } else {
                    nextPosition += 3
                }
            }
            6 -> {
                val in1 = program[currentPosition + 1]
                val in1Mode = params % 10
                val in2 = program[currentPosition + 2]
                val in2Mode = params / 10
                if (readParam(in1, in1Mode) == 0) {
                    nextPosition = readParam(in2, in2Mode)
                } else {
                    nextPosition += 3
                }
            }
            7 -> {
                val in1 = program[currentPosition + 1]
                val in1Mode = params % 10
                val in2 = program[currentPosition + 2]
                val in2Mode = params / 10
                val out = program[currentPosition + 3]
                if (readParam(in1, in1Mode) < readParam(in2, in2Mode)) {
                    program[out] = 1
                } else {
                    program[out] = 0
                }
                nextPosition += 4
            }
            8 -> {
                val in1 = program[currentPosition + 1]
                val in1Mode = params % 10
                val in2 = program[currentPosition + 2]
                val in2Mode = params / 10
                val out = program[currentPosition + 3]
                if (readParam(in1, in1Mode) == readParam(in2, in2Mode)) {
                    program[out] = 1
                } else {
                    program[out] = 0
                }
                nextPosition += 4
            }
        }
        return nextPosition
    }

    fun run() {
        pause = false
        while (program[currentPosition] != 99 && !pause) {
            currentPosition = processCode()
        }
        if (program[currentPosition] == 99) {
            running = false
        }
    }
}

fun main() {
    val outputs = mutableSetOf<Int>()
    val phaseCombinations = PermutationIterator<Int>(listOf(5,6,7,8,9))
    phaseCombinations.forEach { phases ->
        val amp1 = IntComp(initialState.toMutableList(), phases[0])
        val amp2 = IntComp(initialState.toMutableList(), phases[1])
        val amp3 = IntComp(initialState.toMutableList(), phases[2])
        val amp4 = IntComp(initialState.toMutableList(), phases[3])
        val amp5 = IntComp(initialState.toMutableList(), phases[4])
        amp1.amp = amp2
        amp2.amp = amp3
        amp3.amp = amp4
        amp4.amp = amp5
        amp5.amp = amp1
        while(amp5.running) {
            amp1.run()
            amp2.run()
            amp3.run()
            amp4.run()
            amp5.run()
        }
        outputs.add(amp5.output)
    }
    println(outputs.max())
}