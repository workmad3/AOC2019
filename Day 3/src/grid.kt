import java.io.File
import kotlin.math.absoluteValue

data class Coordinate(val x: Int, val y: Int)
data class WireSection(val direction: String, val distance: Int)

fun shortestDistance(wire1: List<Coordinate>, wire2: List<Coordinate>):Int {
    val distance: Int? = intersectionDistances(wire1, wire2).min()
    distance ?: return 0
    return distance
}

fun shortestSteps(wire1: List<Coordinate>, wire2: List<Coordinate>):Int {
    val distance: Int? = intersectionSteps(wire1, wire2).min()
    distance ?: return 0
    return distance
}

fun intersectionDistances(wire1: List<Coordinate>, wire2: List<Coordinate>):Set<Int> {
    return wireIntersections(wire1, wire2).toList().map { it.x.absoluteValue + it.y.absoluteValue }.toSet()
}

fun intersectionSteps(wire1:List<Coordinate>, wire2:List<Coordinate>):Set<Int> {
    val intersections = wireIntersections(wire1, wire2)
    return intersections.toList().map {
        wire1.indexOf(it) + wire2.indexOf(it)
    }.toSet()
}

fun wireIntersections(wire1: List<Coordinate>, wire2: List<Coordinate>):Set<Coordinate> {
    return wire1.intersect(wire2).minus(Coordinate(0,0))
}

fun addLine(section: WireSection, wire: MutableList<Coordinate>) {
    section.distance.downTo(1).forEach {
        val last: Coordinate = wire.last()
        when(section.direction) {
           "U" -> wire.add(Coordinate(last.x, last.y + 1))
           "D" -> wire.add(Coordinate(last.x, last.y - 1))
           "L" -> wire.add(Coordinate(last.x - 1, last.y))
           "R" -> wire.add(Coordinate(last.x + 1, last.y))
        }
    }
}

fun createWire(directions: List<WireSection>):List<Coordinate> {
    val wire:MutableList<Coordinate> = mutableListOf(Coordinate(0, 0))
    directions.forEach { addLine(it, wire)}
    return wire
}

fun main() {
    var wires = mutableListOf<List<Coordinate>>()
    File("C:\\Users\\workm\\IdeaProjects\\AOC2019-3\\input").forEachLine {
        val wireDirections = it.split(",").map {
            val direction = it.get(0)
            val distance = it.drop(1)
            WireSection(direction.toString(), distance.toInt())
        }
        wires.add(createWire(wireDirections))
    }
    print(shortestDistance(wires[0], wires[1]))
    print("\n")
    print(shortestSteps(wires[0], wires[1]))
}