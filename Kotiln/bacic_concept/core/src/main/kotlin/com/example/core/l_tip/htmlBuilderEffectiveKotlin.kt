package com.example.core.l_tip


class TdBuilder {
    var text = ""

    operator fun String.unaryPlus() {
        text += this
    }
}

class TrBuilder {
    var tds = listOf<TdBuilder>()

    fun td(init: TdBuilder.() -> Unit) {
        tds = tds + TdBuilder().apply(init)
    }
}

class TableBuilder {
    var trs = listOf<TrBuilder>()

    fun tr(init: TrBuilder.() -> Unit) {
        trs = trs + TrBuilder().apply(init)
    }
}

fun table(init: TableBuilder.() -> Unit): TableBuilder =
    TableBuilder().apply(init)

/**
 * 복잡한 객체를 만들 때 사용할 수 있는 DSL 활용 방법이다.
 */
fun main() {
    val table: TableBuilder = table {
        tr {
            td {
                +"column 1"
            }
            td {
                +"column 2"
            }
        }
    }
}