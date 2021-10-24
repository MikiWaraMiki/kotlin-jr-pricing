package domain.model.discount.rate

/**
 * 割引適用率
 */
enum class DiscountRate(val value: Int) {
    NO_DISCOUNT(0),
    RATE_10(10),
    RATE_15(15);
}