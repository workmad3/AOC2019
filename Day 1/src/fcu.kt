import java.io.File

fun calculateFuel(weight: Int): Int {
   val fuelNeeded = (weight / 3) - 2
   if (fuelNeeded <= 0) {
      return 0
   }
   return fuelNeeded + calculateFuel(fuelNeeded)
}

fun main() {
   File("C:\\Users\\workm\\IdeaProjects\\AOC2019-1\\input.txt").useLines {
      val weights = it.map { i -> i.toInt() }
      val fuel = weights.map { i -> calculateFuel(i) }
      print(fuel.sum())
   }
}
