package jrpricing.discount.domain.discountrate

import jrpricing.discount.domain.shared.Amount

/**
 * 割引率
 */
enum class DiscountRate(val percent: Percent) {
    NO_DISCOUNT(Percent(0)),
    TEN_PERCENT(Percent(10)),
    FIFTEEN_PERCENT(Percent(15));
}