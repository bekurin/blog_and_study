package enterprise.login.security.token

interface RefreshTokenService {
    fun issue(userId: String): String
    fun rotate(oldToken: String): Pair<String, String>?
    fun revoke(token: String)
    fun getUserId(token: String): String?
}
