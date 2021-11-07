package jrpricing.demo.first.domain.model.discount

import java.math.BigDecimal

/**
 * 割引適用率
 */
enum class DiscountRate(val value: Double) {
    NO_DISCOUNT(0.0),
    RATE_10(10.0),
    RATE_15(15.0);

    fun few(): Double {
        return value / 100
    }
}