package evgeniy.ryzhikov.guesstheflag.utils

import kotlin.random.Random

object NameGenerator {
    private val prefixes = listOf(
        "White",
        "Black",
        "Evil",
        "Good",
        "Brave",
    )

    private val titles = listOf(
        "Mage",
        "Lord",
        "Knight",
        "Warrior",
        "King",
        "Prince",
        "Duke",
        "Baron",
        "Witch",
        "Tsar",
        "Hunter"
    )

    private val names = listOf(
        "Adrian",
        "Adelio",
        "Amara",
        "Augustus",
        "Andrew",
        "Arthur",
        "Alexander",
        "Edmund",
        "Edgar",
        "Anthony",
        "Gerald",
        "Henry",
        "Lancelot",
        "Michael",
        "Madison",
        "Napoleon",
        "Richard",
        "Xander",
        "Titus",
        "Spencer",
        "Bradley",
        "Chad",
        "Eric",
        "Cedric",
        "Daniel",
        "George",
        "Juan",
        "James",
        "Julian",
        "Kaiser",
        "Lad",
        "Philip",
        "Ricardo",
        "Solomon",
        "Steve",
        "Ralph",
        "Terry",
        "Ted",
        "Torvald",
        "William",
        "Vladimir",
        "Vlad",
        "Leroy",
        "Ladomir",
        "Herald",
        "Rex",
        "Sire",
        "Leroi",
        "Sargon",
        "Thanos",
        "Zoltar",
        "Windsor",
        "Conrad",
        "Basil",
        "Balder",
        "Alaric",
        "Darian",
        "Delroy",
        "Donovan",
        "Hector",
        "Idris",
        "Percy",
        "Malik",
        "Minos",
        "Simon",
        "Silvio",
        "Rooney",
        "Tor",
        "Richie",
        "Andrea",
        "Archie",
        "Charles",
        "Alexis",
        "Wilson",
        "Wang",
    )

    fun getRandomName(): String {

        var result : String = ""

        if (Random.nextInt(0, 100) < 30) {
            result += getRandomFromList(prefixes)
        }

        if (Random.nextInt(0, 100) < 60) {
            result += getRandomFromList(titles)
        }

        result += getRandomFromList(names)
        result += Random.nextInt(0, 999).toString()

        return result
    }

    private fun getRandomFromList(list: List<String>): String {
        val rand = Random.nextInt(0, list.size)
        return list[rand]
    }
}