import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

val mapWidth = 31
val mapHeight = 31

data class AsteroidMap(val mapWidth:Int, val mapHeight:Int) {
    private val map = List(mapWidth) { List(mapHeight) { MapElement() } }

    fun setAsteroid(location:Coordinate) {
        map[location.x][location.y].asteroid = true
    }

    fun isAsteroid(location:Coordinate):Boolean {
        if (location.x >= mapWidth || location.x < 0 || location.y >= mapHeight || location.y < 0) {
            return false
        }
        return map[location.x][location.y].asteroid
    }

    fun isVisible(from:Coordinate, target:Coordinate):Boolean {
        if (isAsteroid(from) && isAsteroid(target) && from != target) {
            val slope = (target.y - from.y).toFloat() / (target.x - from.x).toFloat()
            (from.x until target.x).forEach {
                val y = slope * (it - from.x) + from.y
                if ((y % 1).absoluteValue < 0.01 || (y % 1).absoluteValue > 0.99) {
                    val location = Coordinate(it, y.roundToInt())
                    if (location != from && location != target && isAsteroid(location)) {
                        println("from: $from, target: $target, location: $location")
                        println("Asteroid blocking")
                        return false
                    }
                }
            }
            return true
        } else {
            return false
        }
    }

    fun visibleCountFor(location:Coordinate):Int {
        if (isAsteroid(location)) {
            var count = 0
            (0 until mapHeight).forEach { y ->
                (0 until mapWidth).forEach { x ->
                    val target = Coordinate(x, y)
                    if (isVisible(location, target)) {
                        count++
                    }
                }
            }
            return count
        }
        return 0
    }

    fun visibleCounts():List<Int> {
        val visibleCounts = mutableListOf<Int>()
        (0 until mapHeight).forEach { y ->
            (0 until mapWidth).forEach { x ->
                visibleCounts.add(visibleCountFor(Coordinate(x, y)))
            }
        }
        return visibleCounts
    }

    fun bestVisibleCount(): Int? {
        return visibleCounts().max()
    }

    fun print() {
        (0 until mapHeight).forEach { y ->
            (0 until mapWidth).forEach { x ->
                if (isAsteroid(Coordinate(x, y))) {
                    print("#")
                } else {
                    print(".")
                }
            }
            print("\n")
        }
    }
}
data class MapElement(var asteroid:Boolean = false)
data class Coordinate(val x:Int, val y:Int)

fun readMap():AsteroidMap {
    val map = AsteroidMap(mapWidth, mapHeight)
    var row = 0
    File("C:\\Users\\workm\\IdeaProjects\\AOC2019\\Day 10\\input").forEachLine {
        var column = 0
        it.split("").filter { i -> i != "" }.forEach { i ->
            if (i == "#") {
                map.setAsteroid(Coordinate(column, row))
            }
            column++
        }
        row++
    }
    return map
}

fun main() {
    val map = readMap()
    print(map.bestVisibleCount())
    map.print()
}