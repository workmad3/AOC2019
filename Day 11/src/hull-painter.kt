val initialState: List<Long> = listOf(3,8,1005,8,301,1106,0,11,0,0,0,104,1,104,0,3,8,102,-1,8,10,1001,10,1,10,4,10,1008,8,0,10,4,10,1002,8,1,29,1,1103,7,10,3,8,102,-1,8,10,101,1,10,10,4,10,108,1,8,10,4,10,1002,8,1,54,2,103,3,10,2,1008,6,10,1006,0,38,2,1108,7,10,3,8,102,-1,8,10,1001,10,1,10,4,10,108,1,8,10,4,10,1001,8,0,91,3,8,1002,8,-1,10,1001,10,1,10,4,10,1008,8,0,10,4,10,101,0,8,114,3,8,1002,8,-1,10,101,1,10,10,4,10,1008,8,1,10,4,10,1001,8,0,136,3,8,1002,8,-1,10,1001,10,1,10,4,10,1008,8,1,10,4,10,1002,8,1,158,1,1009,0,10,2,1002,18,10,3,8,102,-1,8,10,101,1,10,10,4,10,108,0,8,10,4,10,1002,8,1,187,2,1108,6,10,3,8,1002,8,-1,10,1001,10,1,10,4,10,108,0,8,10,4,10,1002,8,1,213,3,8,1002,8,-1,10,101,1,10,10,4,10,1008,8,1,10,4,10,1001,8,0,236,1,104,10,10,1,1002,20,10,2,1008,9,10,3,8,102,-1,8,10,101,1,10,10,4,10,108,0,8,10,4,10,101,0,8,269,1,102,15,10,1006,0,55,2,1107,15,10,101,1,9,9,1007,9,979,10,1005,10,15,99,109,623,104,0,104,1,21102,1,932700598932,1,21102,318,1,0,1105,1,422,21102,1,937150489384,1,21102,329,1,0,1105,1,422,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,21101,46325083227,0,1,21102,376,1,0,1106,0,422,21102,3263269927,1,1,21101,387,0,0,1105,1,422,3,10,104,0,104,0,3,10,104,0,104,0,21102,988225102184,1,1,21101,410,0,0,1105,1,422,21101,868410356500,0,1,21102,1,421,0,1106,0,422,99,109,2,21202,-1,1,1,21102,1,40,2,21102,1,453,3,21102,1,443,0,1105,1,486,109,-2,2106,0,0,0,1,0,0,1,109,2,3,10,204,-1,1001,448,449,464,4,0,1001,448,1,448,108,4,448,10,1006,10,480,1102,1,0,448,109,-2,2106,0,0,0,109,4,1201,-1,0,485,1207,-3,0,10,1006,10,503,21101,0,0,-3,22101,0,-3,1,21201,-2,0,2,21102,1,1,3,21101,0,522,0,1105,1,527,109,-4,2106,0,0,109,5,1207,-3,1,10,1006,10,550,2207,-4,-2,10,1006,10,550,22102,1,-4,-4,1105,1,618,21201,-4,0,1,21201,-3,-1,2,21202,-2,2,3,21102,569,1,0,1106,0,527,22101,0,1,-4,21101,0,1,-1,2207,-4,-2,10,1006,10,588,21102,1,0,-1,22202,-2,-1,-2,2107,0,-3,10,1006,10,610,21201,-1,0,1,21101,610,0,0,105,1,485,21202,-2,-1,-2,22201,-4,-2,-4,109,-5,2105,1,0)

enum class Direction(val direction:Int) {
    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3);

    fun turnRight():Direction {
        return when(direction) {
            0 -> RIGHT
            1 -> DOWN
            2 -> LEFT
            3 -> UP
            else -> this
        }
    }

    fun turnLeft():Direction {
        return when(direction) {
            0 -> LEFT
            1 -> UP
            2 -> RIGHT
            3 -> DOWN
            else -> this
        }
    }


}

data class Coordinate(val x:Int, val y:Int) {
    fun turn(direction:Direction):Coordinate {
        return when(direction) {
            Direction.UP -> up()
            Direction.DOWN -> down()
            Direction.LEFT -> left()
            Direction.RIGHT -> right()
        }
    }
    fun right():Coordinate {
        return Coordinate(x + 1, y)
    }

    fun left():Coordinate {
        return Coordinate(x - 1, y)
    }

    fun up():Coordinate {
        return Coordinate(x, y + 1)
    }

    fun down():Coordinate {
        return Coordinate(x, y - 1)
    }
}

class IntComp(val initialState: MutableList<Long>) {
    private val program = MutableList<Long>(100000) { 0 }
    var outputCount:Int = 0
    var output:Long = 0
    var relativeBase:Long = 0
    var currentDirection:Direction = Direction.UP
    var hullPainterLocation:Coordinate = Coordinate(0, 0)
    val paintedLocations:MutableSet<Coordinate> = mutableSetOf()
    val paintColours:MutableMap<Coordinate, Long> = mutableMapOf(Pair(Coordinate(0,0), 1L))
    val image:List<MutableList<Int>> = MutableList(7) { MutableList(50) { 0 } }

    private var currentPosition:Long = 0

    private fun readInput():Long {
        if (paintColours.containsKey(hullPainterLocation)) {
            return paintColours[hullPainterLocation] ?: 0L
        }
        return 0L
    }

    private fun writeOutput(value: Long) {
        outputCount++
        if (outputCount % 2 == 0) {
            when(value) {
                0L -> currentDirection = currentDirection.turnLeft()
                1L -> currentDirection = currentDirection.turnRight()
            }
            hullPainterLocation = hullPainterLocation.turn(currentDirection)
        } else {
            paintedLocations.add(hullPainterLocation)
            paintColours[hullPainterLocation] = value
            image[hullPainterLocation.y + 5][hullPainterLocation.x] = value.toInt()
        }
        output = value
    }

    fun printImage() {
        image.reversed().forEach { row ->
            row.forEach { panel ->
                if (panel == 0) {
                    print(" ")
                } else {
                    print("H")
                }
            }
            print("\n")
        }
    }

    private fun readParam(value:Long, mode:Long):Long {
        val immediate:Boolean = (mode.toInt() == 1)
        val relative:Boolean = (mode.toInt() == 2)

        if (immediate) {
            return value
        }
        if (relative) {
            return program[(relativeBase + value).toInt()]
        }
        return program[value.toInt()]
    }

    private fun writeParam(location: Long, mode: Long, value:Long) {
        val relative:Boolean = (mode.toInt() == 2)
        if (relative) {
            program[(relativeBase + location).toInt()] = value
        } else {
            program[location.toInt()] = value
        }
    }

    private fun processCode(): Long {
        val code = program[currentPosition.toInt()] % 100
        val params = program[currentPosition.toInt()] / 100

        var nextPosition:Long = currentPosition
        when(code.toInt()) {
            1 -> {
                val in1 = program[(currentPosition + 1).toInt()]
                val in1Mode = params % 10
                val in2 = program[(currentPosition + 2).toInt()]
                val in2Mode = (params / 10) % 10
                val out = program[(currentPosition + 3).toInt()]
                val outMode = params / 100
                val value = readParam(in1, in1Mode) + readParam(in2, in2Mode)
                writeParam(out, outMode, value)
                nextPosition += 4
            }
            2 -> {
                val in1 = program[(currentPosition + 1).toInt()]
                val in1Mode = params % 10
                val in2 = program[(currentPosition + 2).toInt()]
                val in2Mode = (params / 10) % 10
                val out = program[(currentPosition + 3).toInt()]
                val outMode = params / 100
                val value = readParam(in1, in1Mode) * readParam(in2, in2Mode)
                writeParam(out, outMode, value)
                nextPosition += 4
            }
            3 -> {
                val out = program[(currentPosition + 1).toInt()]
                val outMode = params % 10
                writeParam(out, outMode, readInput())
                nextPosition += 2
            }
            4 -> {
                val in1 = program[(currentPosition + 1).toInt()]
                val in1Mode = params % 10
                writeOutput(readParam(in1, in1Mode))
                nextPosition += 2
            }
            5 -> {
                val in1 = program[(currentPosition + 1).toInt()]
                val in1Mode = params % 10
                val in2 = program[(currentPosition + 2).toInt()]
                val in2Mode = params / 10
                if (readParam(in1, in1Mode) != 0.toLong()) {
                    nextPosition = readParam(in2, in2Mode)
                } else {
                    nextPosition += 3
                }
            }
            6 -> {
                val in1 = program[(currentPosition + 1).toInt()]
                val in1Mode = params % 10
                val in2 = program[(currentPosition + 2).toInt()]
                val in2Mode = params / 10
                if (readParam(in1, in1Mode) == 0.toLong()) {
                    nextPosition = readParam(in2, in2Mode)
                } else {
                    nextPosition += 3
                }
            }
            7 -> {
                val in1 = program[(currentPosition + 1).toInt()]
                val in1Mode = params % 10
                val in2 = program[(currentPosition + 2).toInt()]
                val in2Mode = (params / 10) % 10
                val out = program[(currentPosition + 3).toInt()]
                val outMode = params / 100
                if (readParam(in1, in1Mode) < readParam(in2, in2Mode)) {
                    writeParam(out, outMode, 1)
                } else {
                    writeParam(out, outMode, 0)
                }
                nextPosition += 4
            }
            8 -> {
                val in1 = program[(currentPosition + 1).toInt()]
                val in1Mode = params % 10
                val in2 = program[(currentPosition + 2).toInt()]
                val in2Mode = (params / 10) % 10
                val out = program[(currentPosition + 3).toInt()]
                val outMode = params / 100
                if (readParam(in1, in1Mode) == readParam(in2, in2Mode)) {
                    writeParam(out, outMode, 1)
                } else {
                    writeParam(out, outMode, 0)
                }
                nextPosition += 4
            }
            9 -> {
                val in1 = program[(currentPosition + 1).toInt()]
                val in1Mode = params % 10
                relativeBase += readParam(in1, in1Mode)
                nextPosition += 2
            }
        }
        return nextPosition
    }

    fun run() {
        program.addAll(0, initialState)
        while (program[currentPosition.toInt()] != 99.toLong()) {
            currentPosition = processCode()
        }
    }
}

fun main() {
    val hullPainter = IntComp(initialState.toMutableList())
    hullPainter.run()
    println(hullPainter.paintedLocations)
    println(hullPainter.paintedLocations.maxBy { it.x })
    println(hullPainter.paintedLocations.minBy { it.x })
    println(hullPainter.paintedLocations.maxBy { it.y })
    println(hullPainter.paintedLocations.minBy { it.y })
    println(hullPainter.paintedLocations.size)
    println(hullPainter.paintColours)
    hullPainter.printImage()
}