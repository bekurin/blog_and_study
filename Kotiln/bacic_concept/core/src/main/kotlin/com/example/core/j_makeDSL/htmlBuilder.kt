package com.example.core.j_makeDSL

import com.example.core.g_hignOrderFunctionAndLambda.joinToString

open class Tag(val name: String) {
    private val children = mutableListOf<Tag>()
    private var content: String = ""

    protected fun <T: Tag> doInit(child: T, init: T.() -> Unit) {
        child.init()
        children.add(child)
    }

    fun content(content: String) {
        this.content = content
    }

    override fun toString(): String {
        return "<$name>$content${children.joinToString("")}</$name>"
    }
}

fun table(init: TABLE.() -> Unit) = TABLE().apply(init)

class TABLE: Tag("table") {
    fun tr(init: TR.() -> Unit) = doInit(TR(), init)
}

class TR: Tag("tr") {
    fun td(init: TD.() -> Unit) = doInit(TD(), init)
}

class TD: Tag("td")

/**
 * DSL createTable 을 만든 예제이다.
 * 수신 객체 지정 람다를 변수에 저장하여 DSL 을 완성하였다.
 */
fun main() {
    val table = table {
        content("테이블 추가")
        tr {
            content("티알 추가")
            td {
                content("티디1 추가")
            }
            td {
                content("티디2 추가")
            }
        }
    }
    println("table = $table")
}