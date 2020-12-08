package me.bristermitten.aoc.day7

object Parser {
    private val regex = "(\\d) (\\w+ \\w+) (bag|bags)(, )?".toRegex()

    private val bags = mutableMapOf<String, Bag>()

    private val bagQueue = mutableMapOf<String, Bag>()

    val parts get() = bags.toMap()

    fun parse(data: String) : Bag {
        val adjective = data.substringBefore(" bags")

        var bagsText = data.substringAfter("contain")
        val parts = regex.findAll(bagsText)
        .map { getBag(it.groups[2]!!.value) to it.groups[1]!!.value.toInt() }
        .toMap()

        bags[adjective]?.let {
            it.parts = parts
            return it
        }
        val bag = Bag(adjective, parts)
        bags[adjective] = bag
        return bag
    }

    private fun getBag(description: String) : Bag {
        return bags[description] ?: Bag(description, mapOf()).apply { bags[description] = this}
    }
}