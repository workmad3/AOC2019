import java.util.regex.Pattern

val range:IntRange = IntRange(172851, 675869)
val validPasswordDouble:Regex = Regex("([^1]|^)11([^1]|$)|" +
        "([^2]|^)22([^2]|$)|" +
        "([^3]|^)33([^3]|$)|" +
        "([^4]|^)44([^4]|$)|" +
        "([^5]|^)55([^5]|$)|" +
        "([^6]|^)66([^6]|$)|" +
        "([^7]|^)77([^7]|$)|" +
        "([^8]|^)88([^8]|$)|" +
        "([^9]|^)99([^9]|$)|" +
        "([^0]|^)00([^0]|$)")
val invalidPasswordDecreasing:Regex = Regex(
    "1.*0|" +
        "2.*1|2.*0|" +
        "3.*2|3.*1|3.*0|" +
        "4.*3|4.*2|4.*1|4.*0|" +
        "5.*4|5.*3|5.*2|5.*1|5.*0|" +
        "6.*5|6.*4|6.*3|6.*2|6.*1|6.*0|" +
        "7.*6|7.*5|7.*4|7.*3|7.*2|7.*1|7.*0|" +
        "8.*7|8.*6|8.*5|8.*4|8.*3|8.*2|8.*1|8.*0|" +
        "9.*8|9.*7|9.*6|9.*5|9.*4|9.*3|9.*2|9.*1|9.*0"
)

fun main() {
    val result:Int = range.count {
        val passwordCandidate:String = it.toString()
        validPasswordDouble.containsMatchIn(passwordCandidate) && !invalidPasswordDecreasing.containsMatchIn(passwordCandidate)
    }
    println(result)
}