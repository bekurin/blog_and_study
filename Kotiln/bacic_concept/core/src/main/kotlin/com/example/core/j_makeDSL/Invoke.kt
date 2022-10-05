package com.example.core.j_makeDSL

import com.example.core.a_kotlinBasic.iterator.printLine

class Greeter(val greeting: String) {
    operator fun invoke(name: String) {
        println("$greeting, $name")
    }
}

data class Issue(
        val id: String, val project: String, val type: String,
        val priority: String, val description: String
)

class ImportantIssuesPredicate(val project: String) : (Issue) -> Boolean {
    override fun invoke(issue: Issue): Boolean {
        return issue.project == project && issue.isImportant()
    }

    private fun Issue.isImportant(): Boolean {
        return type == "Bug" &&
                (priority == "Major" || priority == "Critical")
    }
}

/**
 * invoke 를 사용하면 객체를 함수처럼 호출할 수 있다.
 * operator 변경자가 붙은 invoke 함수 정의가 들어있는 클래스의 객체를 함수처럼 호출할 수 있다.
 */
fun main() {
    val bavarianGreeter = Greeter("Servus")
    bavarianGreeter("Dmitry")
    printLine()

    val issue1 = Issue("IDEA-154446", "IDEA", "Bug", "Major", "Save settings failed")
    val issue2 = Issue("KT-12183", "Kotlin", "Feature", "Normal", "Intention: convert several calls on the same receiver to with/apply")

    val predicate = ImportantIssuesPredicate("IDEA")

    for (issue in listOf(issue1, issue2).filter { item ->
        predicate(item)
    }) {
        println(issue.id)
    }
}