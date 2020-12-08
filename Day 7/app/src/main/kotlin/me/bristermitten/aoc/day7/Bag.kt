package me.bristermitten.aoc.day7

data class Bag(
    val adjectives: String,
    var parts: Map<Bag, Int>
) {
    fun canHoldShinyGold() : Boolean {
        return parts.keys.any {
            it.adjectives == "shiny gold" || it.canHoldShinyGold()
        }
    }

    fun sumComponents() : Int {
        return parts.entries.sumBy {
            val sum = it.key.sumComponents()
            it.value * sum.coerceAtLeast(1)
        } + 1
        //add 1 since there's itself too
    }
}
