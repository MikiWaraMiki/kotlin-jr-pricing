package jrpricing.demo.first.usecase.price

data class PriceCalculationUsecaseDto(
    val farePrice: Int,
    val surchargePrice: Int,
    val total: Int = farePrice + surchargePrice
)