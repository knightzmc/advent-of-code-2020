package me.bristermitten.aoc.day7


fun main(args: Array<String>) {
    val data = Parser.javaClass.classLoader.getResourceAsStream("day7.txt").reader().readLines()
    data.forEach { 
        Parser.parse(it)
    }

    val bags = Parser.parts
    println(bags.values.count {
        it.canHoldShinyGold()
    })

    val shinyGold = bags["shiny gold"]!!
    println(shinyGold.sumComponents() - 1) //We don't care about the shiny gold itself
}



