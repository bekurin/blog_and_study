package core.paymentservice.domain

data class PaymentOrder(
    val paymentEventId: Int,
    val sellerId: Int,
    val productId: Int,
    val orderKey: String,
    val amount: Long,
    val paymentStatus: PaymentStatus,
    private val isLedgerUpdated: Boolean = false,
    private val isWalletUpdated: Boolean = false
) : BaseEntity()

enum class PaymentStatus(description: String) {
    NOT_STARTED("결제 처리 시작 전"),
    EXECUTING("결제 처리 중"),
    SUCCESS("결제 처리 성공"),
    FAILURE("결제 처리 실패"),
    UNKNOWN("알 수 없는 결제 상태")
}
