package chapter3

data class User(
    val email: String,
    val rec_count: Int
)

enum class Rank {
    GOOD, BAD, BEST
}

data class Coupon(
    val code: String,
    val rank: Rank
)

data class Email(
    val from: String,
    val to: String,
    val subject: String,
    val body: String
)

fun getUserList(): List<User> {
    return listOf(
        User("john@coldmail.com", 2),
        User("sam@pmail.co", 16),
        User("linda1989@oal.com", 1),
        User("jan1940@ahoy.com", 0),
        User("mrbig@pmail.co", 25)
    )
}

fun getCouponList(): List<Coupon> {
    return listOf(
        Coupon("MAYDISCOUNT", Rank.GOOD),
        Coupon("10PERCENT", Rank.BAD),
        Coupon("PROMOTION", Rank.BEST),
        Coupon("GETADEAL", Rank.BEST),
        Coupon("ILIKEDISCOUNTS", Rank.GOOD)
    )
}