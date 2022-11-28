package chapter8

import util.printLine

class Action(
    val beforeAction: BeforeAction = PrintBefore(),
    val afterAction: AfterAction = PrintAfter()
) : BeforeAction by beforeAction, AfterAction by afterAction

interface BeforeAction {
    fun doSomethingBefore(name: String): String
}

interface AfterAction {
    fun doSomethingAfter(name: String): String
}

class PrintBefore : BeforeAction {
    override fun doSomethingBefore(name: String) = "PrintBefore(name=$name)"
}

class PrintAfter : AfterAction {
    override fun doSomethingAfter(name: String) = "PrintAfter(name=$name)"
}

fun main() {
    val action = Action()
    println("action.doSomethingBefore(\"hello!\") = ${action.doSomethingBefore("hello!")}")
    println("action.doSomethingAfter(\"bye!\") = ${action.doSomethingAfter("bye!")}")
    printLine()

    /**
     * SYNCHRONIZED: 오직 하나의 스레드만 Lazy 인스턴스를 초기화할 수 있게 락을 사용
     * PUBLICATION: 초기화 함수가 여러 번 호출될 수 있지만 첫 번째 리턴값만 사용됨
     * NONE: 락이 사용되지 않음
     */
    val ultimateAnswer: Int by lazy(
        mode = LazyThreadSafetyMode.PUBLICATION
    ) {
        println("computing the answer!")
        42
    }
    println("ultimateAnswer1 = $ultimateAnswer")
    println("ultimateAnswer2 = $ultimateAnswer")
    println("ultimateAnswer3 = $ultimateAnswer")
}

