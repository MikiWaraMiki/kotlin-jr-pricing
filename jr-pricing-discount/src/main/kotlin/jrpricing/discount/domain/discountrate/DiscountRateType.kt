package jrpricing.discount.domain.discountrate

/**
 * 割引率種別
 */
enum class DiscountRateType(val percent: Percent) {
    NO_DISCOUNT(Percent(0)),
    TEN_PERCENT(Percent(10)),
    FIFTEEN_PERCENT(Percent(15))
}