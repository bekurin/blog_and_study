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

data class Member(val nickname: String)

data class Card(val value: Value, val pattern: Pattern)

class Deck() {
    private var cards: MutableList<Card> = mutableListOf()

    init {
        initDeck()
    }

    private fun initDeck(): List<Card> {
        cards.clear()
        cards = Pattern.values()
            .flatMap { pattern ->
                return@flatMap Value.values()
                    .map { value ->
                        return@map Card(value, pattern)
                    }
            }.toMutableList()
        return cards
    }

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

    fun isEmpty(): Boolean {
        return cards.isEmpty()
    }
}

class GameManager {
    private val deck = mutableListOf<Card>()
    private val member = mutableListOf<Member>()

    fun addCard(card: Card) {

    }

    fun getWinner() {

    }
}

fun main() {
    val deck = Deck()
    println(deck.shuffle())
    while (deck.isEmpty().not()) {
        println(deck.drawCard())
    }
}
