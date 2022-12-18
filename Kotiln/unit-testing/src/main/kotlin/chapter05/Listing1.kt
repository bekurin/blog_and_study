package chapter05;

public class Listing1 {
    class Controller(
        var emailGateway: EmailGateway?,
        var database: Database?,
    ) {

        constructor(emailGateway: EmailGateway?) : this(emailGateway, null)
        constructor(database: Database?): this(null, database)

        fun greetUser(userEmail: String) {
            emailGateway?.sendGreetingsEmail(userEmail)
        }

        fun createReport(): Report {
            val numberOfUsers = database?.getNumberOfUsers() ?: 0
            return Report(numberOfUsers)
        }
    }

    class Report(
        val numberOfUsers: Int,
    )

    interface Database {
        fun getNumberOfUsers(): Int
    }

    interface EmailGateway {
        fun sendGreetingsEmail(userEmail: String)
    }
}
