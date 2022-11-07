package chapter3

fun selectCouponByRank(coupons: List<Coupon>, rank: Rank): List<Coupon> {
    var ret: MutableList<Coupon> = mutableListOf()
    for (coupon in coupons) {
        if (coupon.rank == rank)
            ret.add(coupon)
    }
    return ret
}

fun subCouponRank(subscriber: User): Rank {
    return if (subscriber.rec_count >= 10) Rank.BEST
    else Rank.GOOD
}

fun emailForSubscriber(subscriber: User, goods: List<Coupon>, bests: List<Coupon>): Email {
    val rank = subCouponRank(subscriber)
    return if (rank == Rank.BEST) {
        Email(
            from = "from@naver.com",
            to = subscriber.email,
            subject = "Your best weekly coupons inside",
            body = "Here are the best coupons: ${bests.joinToString()}"
        )
    } else {
        Email(
            from = "from@naver.com",
            to = subscriber.email,
            subject = "Your good weekly coupons inside",
            body = "Here are the good coupons: ${goods.joinToString()}"
        )
    }
}

fun emailForSubscribers(subscribers: List<User>, goods: List<Coupon>, bests: List<Coupon>): List<Email> {
    var emails: MutableList<Email> = mutableListOf()
    for (subscriber in subscribers) {
        val email = emailForSubscriber(subscriber, goods, bests)
        emails.add(email)
    }
    return emails
}

fun send(email: Email) {
    println(email)
}

/**
 * 액션은 실행 시점과 횟수가 중요한 것을 말한다.
 * 계산은 같은 데이터를 넣었다면 항상 같은 결과를 반환하는 것을 말한다.
 * send, getUserList, getCouponList와 같은 연산은 액션이다.
 */
fun main() {
    val userList = getUserList()
    val couponList = getCouponList()
    val bestCoupons = selectCouponByRank(couponList, Rank.BEST)
    val goodCoupons = selectCouponByRank(couponList, Rank.GOOD)
    val emails = emailForSubscribers(userList, goodCoupons, bestCoupons)
    for (email in emails) {
        send(email)
    }
}