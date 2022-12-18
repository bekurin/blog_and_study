package chapter05

class Listing5 {
    class User(
        var name: String = "",
    ) {
        fun normalizeName(name: String): String {
            val result = name.trim()

            if (result.length > 50) {
                return result.substring(0..50)
            }
            return result
        }
    }

    class UserController {
        private var id: Int = 0
        private val database: HashMap<Int, User> = hashMapOf()

        fun renameUser(userId: Int, newName: String) {
            val user = getUserFromDatabase(userId)

            val normalizeName = user.normalizeName(newName)
            user.name = normalizeName

            saveUserToDatabase(user)
        }

        private fun saveUserToDatabase(user: User) {
            database[id++] = user
        }

        private fun getUserFromDatabase(userId: Int): User {
            return database[userId] ?: throw RuntimeException("${userId}를 가진 User를 찾을 수 없습니다.")
        }

    }
}