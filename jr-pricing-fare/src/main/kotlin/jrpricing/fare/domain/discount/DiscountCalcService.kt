package jrpricing.fare.domain.discount

import jrpricing.fare.domain.fare.BasicFare
import jrpricing.fare.domain.fare.Fare
import jrpricing.fare.domain.shared.Amount
import jrpricing.fare.domain.shared.DepartureDate
import jrpricing.fare.domain.shared.Passenger
import jrpricing.fare.domain.shared.TripRoute
import org.springframework.stereotype.Component

/**
 * 割引料金計算サービス
 */
@Component
class DiscountCalcService(
    private val discountRepository: DiscountRepository
) {
    fun calc(basicFare: BasicFare, tripRoute: TripRoute, passenger: Passenger, departureDate: DepartureDate): Fare {
        val discountedResult = discountRepository.calc(basicFare, tripRoute, passenger, departureDate)

        val adjustedDiscountedResult = Amount.withAdjust(discountedResult.value)

        return Fare.of(adjustedDiscountedResult)
    }
}