package chapter05

class Listing6 {
    class User {
        private var _name: String = ""
        var name: String
            get() {
                return _name
            }
            set(value) {
                _name = normalizeName(value)
            }

        private fun normalizeName(name: String): String {
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
            user.name = newName
            updateUserFromDatabase(userId, user)
        }

        private fun saveUserToDatabase(user: User) {
            database[id++] = user
        }

        private fun updateUserFromDatabase(userId: Int, user: User) {
            database[userId] = user
        }

        private fun getUserFromDatabase(userId: Int): User {
            return database[id] ?: throw RuntimeException("${userId}를 가진 user를 찾을 수 없습니다")
        }
    }
}