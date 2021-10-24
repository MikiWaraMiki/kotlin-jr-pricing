package domain.model.discount.rate

import java.math.BigDecimal

/**
 * 割引適用率
 */
enum class DiscountRate(val value: Double) {
    NO_DISCOUNT(0.0),
    RATE_10(10.0),
    RATE_15(15.0);
}