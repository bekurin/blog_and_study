package com.example.core.l_tip

enum class Pattern {
    SPADE, CLOVER, HEART, DIAMOND
}

enum class Value(val value: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13)
}

data class Card(val value: Value, val pattern: Pattern)

data class Deck(
    private val cards: MutableList<Card> = Pattern.values()
        .flatMap { pattern ->
            return@flatMap Value.values()
                .map { value ->
                    return@map Card(value, pattern)
                }
        }.toMutableList()
) : List<Card> by cards {

    fun shuffle(): List<Card> {
        cards.shuffle()
        return cards
    }

    fun drawCard(): Card {
        val first = cards.first()
        if (cards.remove(first)) {
            return first
        }
        throw RuntimeException("카드가 없습니다. $first")
    }
}

fun main() {
    val deck = Deck()
    println(deck.shuffle())
    while (deck.isNotEmpty()) {
        println(deck.drawCard())
    }
}
