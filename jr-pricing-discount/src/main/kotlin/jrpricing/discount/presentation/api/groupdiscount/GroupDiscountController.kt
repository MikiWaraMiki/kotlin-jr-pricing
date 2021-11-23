package jrpricing.discount.presentation.api.groupdiscount

import jrpricing.discount.domain.roundtrip.BeforeRoundTripDiscountedFare
import jrpricing.discount.domain.shared.Amount
import jrpricing.discount.domain.shared.TripType
import jrpricing.discount.usecase.roundtrip.CalcRoundTripDiscountUsecase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/discount/group_discount")
class GroupDiscountController(
    private val calcGroupDiscountUsecase: CalcRoundTripDiscountUsecase
) {

    @GetMapping("/calc")
    fun calc(
        @RequestParam(name = "departureStationId") departureStationId: String,
        @RequestParam(name = "arrivalStationId") arrivalStationId: String,
        @RequestParam(name = "tripTypeName") tripTypeName: String,
        @RequestParam(name = "fare") fare: Int
    ): GroupDiscountCalcResponse {
        val discountedResultAmount = calcGroupDiscountUsecase.execute(
            departureStationId,
            arrivalStationId,
            getTripType(tripTypeName),
            fare
        )

        return GroupDiscountCalcResponse(
            amount = discountedResultAmount.value
        )
    }

    private fun getTripType(tripTypeName: String): TripType {
        return TripType.of(tripTypeName)
    }
}