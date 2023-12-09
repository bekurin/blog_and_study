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

/**
 * List<Card>를 합성하여 Deck의 내부에서는 Cards를 mutableList로 다루지만 Deck의 외부에서는 List<Card>로 다루도록 한다.
 * 이렇게 하지 않았다면 MutableList로 Cards를 선언하여 언제 어디서 변경되어도 이상하지 않았지만 일급 컬렉션을 사용하므로써 내부에서만 값이 변경될 수 있다.
 */
data class Deck(
    private val cards: MutableList<Card>
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
    val cards = Pattern.values()
        .flatMap { pattern ->
            return@flatMap Value.values()
                .map { value ->
                    return@map Card(value, pattern)
                }
        }.toMutableList()
    val deck = Deck(cards)
    println(deck.shuffle())
    while (deck.isNotEmpty()) {
        println(deck.drawCard())
    }
}
