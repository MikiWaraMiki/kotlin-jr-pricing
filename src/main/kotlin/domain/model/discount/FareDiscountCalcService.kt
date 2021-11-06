package domain.model.discount

import domain.model.discount.distance.DistanceDiscount
import domain.model.discount.distance.DistanceKilometerDiscountRule
import domain.model.discount.group.GroupDiscount
import domain.model.discount.group.GroupDiscountRule
import domain.model.discount.largegroup.LargeGroupDiscountRule
import domain.model.discount.largegroup.LargeGroupFareDiscount
import domain.model.fare.Fare
import domain.model.route.Route
import domain.model.shared.Passengers
import domain.model.shared.Price
import domain.model.ticket.DepartureDate
import domain.model.ticket.TripType

/**
 * 運賃割引計算サービス
 */
class FareDiscountCalcService(
    private val distanceDiscountRule: DiscountRule,
    private val groupDiscountRule: DiscountRule,
    private val largeGroupDiscountRule: DiscountRule
) {

    fun calc(
        fare: Fare,
        passengers: Passengers,
        departureDate: DepartureDate,
    ): Price {
        val afterDiscountedFare = if (distanceDiscountRule.can()) {
            val distanceDiscount = DistanceDiscount(fare)
            val resultPrice = distanceDiscount.afterDiscounted()
            Fare(resultPrice)
        } else {
            fare
        }

        return when {
            groupDiscountRule.can() -> {
                val groupDiscount = GroupDiscount.fromDepartureDate(afterDiscountedFare, departureDate)
                groupDiscount.afterDiscounted()
            }
            largeGroupDiscountRule.can() -> {
                val largeGroupFareDiscount = LargeGroupFareDiscount(afterDiscountedFare, passengers)
                return largeGroupFareDiscount.afterDiscounted()
            }
            else -> {
                afterDiscountedFare.price(false)
            }
        }
    }

    companion object {
        fun withGenerateRule(tripType: TripType, tripDistance: Int, passengers: Passengers): FareDiscountCalcService {
            val distanceDiscountRule = DistanceKilometerDiscountRule(tripType, tripDistance)
            val groupDiscountRule = GroupDiscountRule(passengers)
            val largeGroupDiscountRule = LargeGroupDiscountRule(passengers)

            return FareDiscountCalcService(
                distanceDiscountRule,
                groupDiscountRule,
                largeGroupDiscountRule
            )
        }
    }
}