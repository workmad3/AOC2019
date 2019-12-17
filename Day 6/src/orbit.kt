import java.io.File
import java.util.*

data class Body(val name: String)
val orbits:MutableMap<Body, Body> = mutableMapOf()



fun main() {
    File("C:\\Users\\workm\\IdeaProjects\\AOC2019-6\\input").forEachLine {
        val orbitData:List<String> = it.split(")")
        val centre = Body(orbitData[0])
        val satellite = Body(orbitData[1])
        orbits[satellite] = centre
    }
    var result = 0
    orbits.forEach() { (body, _) ->
        var next:Body? = orbits[body]
        while (next != null) {
            result += 1
            next = orbits[next]
        }
    }
    println(result)

    var santa = orbits[Body("SAN")]
    var santaPath = mutableListOf<Body?>()
    while(santa != null) {
        santaPath.add(santa)
        santa = orbits[santa]
    }
    var you = orbits[Body("YOU")]
    var youPath = mutableListOf<Body?>()
    while (you != null) {
        youPath.add(you)
        you = orbits[you]
    }
    val sharedPath = santaPath.intersect(youPath)
    santaPath = santaPath.minus(sharedPath).toMutableList()
    youPath = youPath.minus(sharedPath).toMutableList()
    println(santaPath)
    println(youPath)
    println(santaPath.size + youPath.size)
}